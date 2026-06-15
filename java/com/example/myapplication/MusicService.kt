package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import android.widget.RemoteViews
import java.io.File

class MusicService : Service() {

    lateinit var musicPlayer: ExoPlayer
    private val binder = MusicBinder()

    var currentSong: MusicItem? = null
        private set
    var downloadedList = listOf<MusicItem>()
        private set

    // 回调通知界面更新的监听
    var onSongChangedListener: ((MusicItem?) -> Unit)? = null
    var onPlayStateChangedListener: ((Boolean) -> Unit)? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "com.example.myapplication.ACTION_PLAY_PAUSE" -> {
                    if (musicPlayer.isPlaying) {
                        pauseSong()
                    } else {
                        resumeSong()
                    }
                }
                "com.example.myapplication.ACTION_PREV" -> {
                    playPrev()
                }
                "com.example.myapplication.ACTION_NEXT" -> {
                    playNext()
                }
                "com.example.myapplication.ACTION_STOP" -> {
                    stopSong()
                }
            }
        }
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        musicPlayer = ExoPlayer.Builder(this).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        playNext()
                    }
                    updateNotification()
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    onPlayStateChangedListener?.invoke(isPlaying)
                    updateNotification()
                }
            })
        }

        // 注册控制广播
        val filter = IntentFilter().apply {
            addAction("com.example.myapplication.ACTION_PLAY_PAUSE")
            addAction("com.example.myapplication.ACTION_PREV")
            addAction("com.example.myapplication.ACTION_NEXT")
            addAction("com.example.myapplication.ACTION_STOP")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }

        createNotificationChannel()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    fun playSong(song: MusicItem, playlist: List<MusicItem>) {
        currentSong = song
        downloadedList = playlist
        onSongChangedListener?.invoke(song)

        val uri = getMusicFileUri(this, "${song.filename}.mp3")
        if (uri != null) {
            musicPlayer.setMediaItem(MediaItem.fromUri(uri))
            musicPlayer.prepare()
            musicPlayer.play()

            // 开启前台服务
            startForegroundServiceCompat()
        }
    }

    fun restoreSong(song: MusicItem, playlist: List<MusicItem>, position: Long) {
        currentSong = song
        downloadedList = playlist
        onSongChangedListener?.invoke(song)

        val uri = getMusicFileUri(this, "${song.filename}.mp3")
        if (uri != null) {
            musicPlayer.setMediaItem(MediaItem.fromUri(uri))
            musicPlayer.prepare()
            musicPlayer.seekTo(position)
        }
    }

    fun resumeSong() {
        if (currentSong != null) {
            musicPlayer.play()
            startForegroundServiceCompat()
        }
    }

    fun pauseSong() {
        musicPlayer.pause()
        updateNotification()
        // 暂停时可以退出前台模式，使其可以被滑动删除 (但保留通知)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_DETACH)
        }
    }

    fun stopSong() {
        musicPlayer.pause()
        musicPlayer.seekTo(0)
        onPlayStateChangedListener?.invoke(false)
        stopForeground(true)
        stopSelf()
    }

    fun playNext() {
        val list = downloadedList.filter { it.isDownloaded }
        if (list.isEmpty() || currentSong == null) return
        val idx = list.indexOfFirst { it.id == currentSong?.id }
        val nextSong = if (idx != -1) {
            list[(idx + 1) % list.size]
        } else {
            list.first()
        }
        playSong(nextSong, list)
    }

    fun playPrev() {
        val list = downloadedList.filter { it.isDownloaded }
        if (list.isEmpty() || currentSong == null) return
        val idx = list.indexOfFirst { it.id == currentSong?.id }
        val prevSong = if (idx != -1) {
            list[(idx - 1 + list.size) % list.size]
        } else {
            list.last()
        }
        playSong(prevSong, list)
    }

    private fun startForegroundServiceCompat() {
        val notification = buildNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1001, notification, android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        } else {
            startForeground(1001, notification)
        }
    }

    private fun updateNotification() {
        if (currentSong != null) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1001, buildNotification())
        }
    }

    private fun loadScaledAssetBitmap(context: Context, assetPath: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        return try {
            context.assets.open(assetPath).use { inputStream ->
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                BitmapFactory.decodeStream(inputStream, null, options)
                options.inPreferredConfig = Bitmap.Config.RGB_565
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
                options.inJustDecodeBounds = false
                
                val decoded = context.assets.open(assetPath).use { stream ->
                    BitmapFactory.decodeStream(stream, null, options)
                }
                
                if (decoded != null) {
                    val scaled = Bitmap.createScaledBitmap(decoded, reqWidth, reqHeight, true)
                    if (scaled != decoded) {
                        decoded.recycle()
                    }
                    scaled
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun buildNotification(): Notification {
        val openIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val openPendingIntent = PendingIntent.getActivity(this, 4, openIntent, PendingIntent.FLAG_IMMUTABLE)

        val collapsedViews = RemoteViews(packageName, R.layout.custom_music_notification).apply {
            setTextViewText(R.id.notification_title, currentSong?.title ?: "音乐播放器")
            setTextViewText(R.id.notification_artist, currentSong?.artist ?: "未知歌手")
            
            val bgBitmap = loadScaledAssetBitmap(this@MusicService, "logo/music.jpg", 600, 300)
            if (bgBitmap != null) {
                setImageViewBitmap(R.id.notification_bg, bgBitmap)
            }
        }

        val expandedViews = RemoteViews(packageName, R.layout.custom_music_notification_large).apply {
            setTextViewText(R.id.notification_title, currentSong?.title ?: "音乐播放器")
            setTextViewText(R.id.notification_artist, currentSong?.artist ?: "未知歌手")
            
            val bgBitmap = loadScaledAssetBitmap(this@MusicService, "logo/music.jpg", 800, 400)
            if (bgBitmap != null) {
                setImageViewBitmap(R.id.notification_bg, bgBitmap)
            }
        }

        return NotificationCompat.Builder(this, "music_playback_channel")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(openPendingIntent)
            .setCustomContentView(collapsedViews)
            .setCustomBigContentView(expandedViews)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music_playback_channel",
                "音乐播放控制",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "用于音乐播放通知栏显示与控制"
                setShowBadge(false)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        musicPlayer.release()
    }
}
