package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Intent
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Build
import androidx.documentfile.provider.DocumentFile
import java.util.Calendar
import java.util.Locale
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.CookieManager
import android.content.pm.ActivityInfo
import androidx.lifecycle.lifecycleScope
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.draw.rotate
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.draw.drawBehind
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Folder
import androidx.compose.animation.core.LinearEasing
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.material3.SliderDefaults
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import java.io.File
import java.io.FileOutputStream
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.graphics.Brush
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.coroutineScope
import androidx.compose.foundation.gestures.awaitFirstDown
import android.media.MediaScannerConnection
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.ConcurrentHashMap

data class WeatherInfo(
    val city: String,
    val temp: String,
    val text: String
)

data class HistoryItem(
    val title: String,
    val url: String,
    val timestamp: Long
)

data class BookmarkItem(
    val title: String,
    val url: String
)

data class DownloadItem(
    val id: Long,
    val fileName: String,
    val url: String,
    val size: String,
    val timestamp: Long,
    val status: String = "Downloading", // "Downloading", "Paused", "Completed", "Failed"
    val progress: Float = 0f,
    val speed: String = "",
    val downloadedBytes: Long = 0L,
    val totalBytes: Long = -1L,
    val downloadId: Long = -1L,
    val savedUserAgent: String = "",
    val savedReferer: String = ""
)

data class SearchEngine(
    val name: String,
    val logoAsset: String,
    val searchUrl: String,
    val suggestUrl: String
)

val searchEngines = listOf(
    SearchEngine("Bing",   "logo/bing-logo-small.png",   "https://www.bing.com/search?q=", "https://api.bing.com/osjson.aspx?query="),
    SearchEngine("Baidu",  "logo/baidu-logo-small.png",  "https://www.baidu.com/s?wd=", "https://suggestion.baidu.com/su?action=opensearch&wd="),
    SearchEngine("Google", "logo/google-logo-small.png", "https://www.google.com/search?q=", "https://suggestqueries.google.com/complete/search?client=firefox&q="),
    SearchEngine("Sogou",  "logo/sogou-logo-small.png",  "https://www.sogou.com/web?query=", "https://suggestion.baidu.com/su?action=opensearch&wd=")
)

// ════════════════════════════════════════
//  壁纸数据
// ════════════════════════════════════════
data class WallpaperItem(
    val id: Int,
    val type: String,          // "video" 或 "static"
    val coverAsset: String,    // assets 中的封面路径
    val downloadUrl: String,
    val fileName: String       // 下载后保存的文件名
)

private const val BASE_URL = "https://ghfast.top/https://raw.githubusercontent.com/shichen1234/androidwallpapers/main/"

val videoWallpapers = (1..10).map { i ->
    WallpaperItem(
        id = i, type = "video",
        coverAsset = "video/$i.png",
        downloadUrl = "${BASE_URL}$i.mp4",
        fileName = "video_$i.mp4"
    )
}

val staticWallpapers = (1..15).map { i ->
    WallpaperItem(
        id = i, type = "static",
        coverAsset = "static/$i.jpg",
        downloadUrl = "${BASE_URL}$i.jpg",
        fileName = "static_$i.jpg"
    )
}

// ════════════════════════════════════════
//  音乐播放器数据模型与下载逻辑
// ════════════════════════════════════════
fun getMusicDownloadUri(context: Context): String? {
    val prefs = context.getSharedPreferences("download_settings", Context.MODE_PRIVATE)
    return prefs.getString("music_download_uri", null)
}

fun getFileDownloadUri(context: Context): String? {
    val prefs = context.getSharedPreferences("download_settings", Context.MODE_PRIVATE)
    return prefs.getString("file_download_uri", null)
}

fun getPathFromDocumentTreeUri(context: Context, uriString: String?, isMusic: Boolean): String {
    if (uriString.isNullOrBlank()) {
        return if (isMusic) {
            File(context.filesDir, "music").absolutePath
        } else {
            android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_DOWNLOADS
            ).absolutePath
        }
    }
    try {
        val uri = Uri.parse(uriString)
        if (uri.scheme == "content") {
            val docId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                android.provider.DocumentsContract.getTreeDocumentId(uri)
            } else {
                uri.path
            }
            if (docId != null) {
                if (docId.startsWith("primary:")) {
                    return "内部存储/" + docId.substringAfter("primary:")
                } else {
                    return "存储盘/" + docId
                }
            }
        }
        return uri.path ?: uriString
    } catch (e: Exception) {
        return uriString
    }
}

fun getOutputStreamForDownload(
    context: Context,
    isMusic: Boolean,
    fileName: String,
    mimeType: String,
    append: Boolean = false
): java.io.OutputStream? {
    val uriString = if (isMusic) getMusicDownloadUri(context) else getFileDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        return if (isMusic) {
            val musicDir = File(context.filesDir, "music").also { it.mkdirs() }
            val file = File(musicDir, fileName)
            FileOutputStream(file, append)
        } else {
            val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_DOWNLOADS
            )
            downloadsDir.mkdirs()
            val file = File(downloadsDir, fileName)
            FileOutputStream(file, append)
        }
    }
    
    try {
        val treeUri = Uri.parse(uriString)
        val documentDir = DocumentFile.fromTreeUri(context, treeUri) ?: return null
        var file = documentDir.findFile(fileName)
        if (file == null) {
            file = documentDir.createFile(mimeType, fileName)
        } else if (!append) {
            file.delete()
            file = documentDir.createFile(mimeType, fileName)
        }
        if (file != null) {
            return context.contentResolver.openOutputStream(file.uri, if (append) "wa" else "rwt")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun deleteMusicFile(context: Context, filename: String, hasTranslation: Boolean): Boolean {
    var success = true
    val uriString = getMusicDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        val musicDir = File(context.filesDir, "music")
        val mp3File = File(musicDir, "$filename.mp3")
        if (mp3File.exists()) {
            if (!mp3File.delete()) success = false
        }
        val lrcFile = File(musicDir, "$filename.lrc")
        if (lrcFile.exists()) {
            lrcFile.delete()
        }
        val lrcFileTxt = File(musicDir, "$filename.lrc.txt")
        if (lrcFileTxt.exists()) {
            lrcFileTxt.delete()
        }
        if (hasTranslation) {
            val transFile = File(musicDir, "$filename--翻译.lrc")
            if (transFile.exists()) {
                transFile.delete()
            }
            val transFileTxt = File(musicDir, "$filename--翻译.lrc.txt")
            if (transFileTxt.exists()) {
                transFileTxt.delete()
            }
        }
    } else {
        try {
            val treeUri = Uri.parse(uriString)
            val documentDir = DocumentFile.fromTreeUri(context, treeUri)
            if (documentDir != null) {
                val mp3File = documentDir.findFile("$filename.mp3")
                if (mp3File != null && mp3File.exists()) {
                    if (!mp3File.delete()) success = false
                }
                val lrcFile = documentDir.findFile("$filename.lrc") ?: documentDir.findFile("$filename.lrc.txt")
                if (lrcFile != null && lrcFile.exists()) {
                    lrcFile.delete()
                }
                if (hasTranslation) {
                    val transFile = documentDir.findFile("$filename--翻译.lrc") ?: documentDir.findFile("$filename--翻译.lrc.txt")
                    if (transFile != null && transFile.exists()) {
                        transFile.delete()
                    }
                }
            } else {
                success = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            success = false
        }
    }
    return success
}

fun checkMusicFileExists(context: Context, fileName: String): Boolean {
    val uriString = getMusicDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        val musicDir = File(context.filesDir, "music")
        return File(musicDir, fileName).exists()
    }
    try {
        val treeUri = Uri.parse(uriString)
        val documentDir = DocumentFile.fromTreeUri(context, treeUri) ?: return false
        val file = documentDir.findFile(fileName)
        return file != null && file.exists()
    } catch (e: Exception) {
        return false
    }
}

fun getMusicFileUri(context: Context, fileName: String): Uri? {
    val uriString = getMusicDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        val musicDir = File(context.filesDir, "music")
        val file = File(musicDir, fileName)
        return if (file.exists()) Uri.fromFile(file) else null
    }
    try {
        val treeUri = Uri.parse(uriString)
        val documentDir = DocumentFile.fromTreeUri(context, treeUri) ?: return null
        val file = documentDir.findFile(fileName)
        return file?.uri
    } catch (e: Exception) {
        return null
    }
}

fun getMusicLyricLines(context: Context, filename: String, isTranslation: Boolean): List<LyricLine> {
    val suffix = if (isTranslation) "--翻译.lrc" else ".lrc"
    val uriString = getMusicDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        val musicDir = File(context.filesDir, "music")
        val lrcFile = File(musicDir, "$filename$suffix")
        return parseLrc(lrcFile)
    }
    try {
        val treeUri = Uri.parse(uriString)
        val documentDir = DocumentFile.fromTreeUri(context, treeUri) ?: return emptyList()
        var file = documentDir.findFile("$filename$suffix")
        if (file == null) {
            file = documentDir.findFile("$filename$suffix.txt")
        }
        if (file == null) return emptyList()
        context.contentResolver.openInputStream(file.uri)?.use { stream ->
            val lines = mutableListOf<LyricLine>()
            val timeRegex = """\[(\d+):(\d+)(?:\.(\d+))?]""".toRegex()
            stream.bufferedReader().useLines { fileLines ->
                fileLines.forEach { line ->
                    val matchResults = timeRegex.findAll(line).toList()
                    if (matchResults.isNotEmpty()) {
                        val text = line.substring(matchResults.last().range.last + 1).trim()
                        for (match in matchResults) {
                            val min = match.groupValues[1].toLong()
                            val sec = match.groupValues[2].toLong()
                            val msStr = match.groupValues.getOrNull(3) ?: "00"
                            val ms = (msStr.padEnd(3, '0').substring(0, 3).toLongOrNull() ?: 0L)
                            val timeMs = min * 60 * 1000 + sec * 1000 + ms
                            lines.add(LyricLine(timeMs, text))
                        }
                    }
                }
            }
            return lines.sortedBy { it.timeMs }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return emptyList()
}

fun writeTextToDownload(context: Context, isMusic: Boolean, fileName: String, text: String) {
    val stream = getOutputStreamForDownload(context, isMusic, fileName, "application/octet-stream")
    stream?.use {
        it.write(text.toByteArray(Charsets.UTF_8))
    }
}

fun checkDownloadFileExists(context: Context, fileName: String): Boolean {
    val uriString = getFileDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
            android.os.Environment.DIRECTORY_DOWNLOADS
        )
        return File(downloadsDir, fileName).exists()
    }
    try {
        val treeUri = Uri.parse(uriString)
        val documentDir = DocumentFile.fromTreeUri(context, treeUri) ?: return false
        val file = documentDir.findFile(fileName)
        return file != null && file.exists()
    } catch (e: Exception) {
        return false
    }
}

fun getDownloadFileUri(context: Context, fileName: String): Uri? {
    val uriString = getFileDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
            android.os.Environment.DIRECTORY_DOWNLOADS
        )
        val file = File(downloadsDir, fileName)
        return if (file.exists()) {
            androidx.core.content.FileProvider.getUriForFile(
                context, "${context.packageName}.fileprovider", file
            )
        } else null
    }
    try {
        val treeUri = Uri.parse(uriString)
        val documentDir = DocumentFile.fromTreeUri(context, treeUri) ?: return null
        val file = documentDir.findFile(fileName)
        return file?.uri
    } catch (e: Exception) {
        return null
    }
}

fun deleteDownloadedFile(context: Context, isMusic: Boolean, fileName: String) {
    val uriString = if (isMusic) getMusicDownloadUri(context) else getFileDownloadUri(context)
    if (uriString.isNullOrBlank()) {
        if (isMusic) {
            val musicDir = File(context.filesDir, "music")
            File(musicDir, fileName).delete()
        } else {
            val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_DOWNLOADS
            )
            File(downloadsDir, fileName).delete()
        }
    } else {
        try {
            val treeUri = Uri.parse(uriString)
            val documentDir = DocumentFile.fromTreeUri(context, treeUri)
            val file = documentDir?.findFile(fileName)
            file?.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class MusicItem(
    val id: Int,
    val filename: String,
    val title: String,
    val artist: String,
    val downloadProgress: Float = -1f, // -1f: 未下载, 0f..1f: 下载进度, 2f: 下载完成
    val isDownloaded: Boolean = false,
    val hasTranslation: Boolean = false
)

data class LyricLine(
    val timeMs: Long,
    val text: String
)

fun parseLrc(file: File): List<LyricLine> {
    if (!file.exists()) return emptyList()
    val lines = mutableListOf<LyricLine>()
    val timeRegex = """\[(\d+):(\d+)(?:\.(\d+))?]""".toRegex()
    
    try {
        file.useLines { fileLines ->
            fileLines.forEach { line ->
                val matchResults = timeRegex.findAll(line).toList()
                if (matchResults.isNotEmpty()) {
                    val text = line.substring(matchResults.last().range.last + 1).trim()
                    for (match in matchResults) {
                        val min = match.groupValues[1].toLong()
                        val sec = match.groupValues[2].toLong()
                        val msStr = match.groupValues.getOrNull(3) ?: "00"
                        val ms = (msStr.padEnd(3, '0').substring(0, 3).toLongOrNull() ?: 0L)
                        val timeMs = min * 60 * 1000 + sec * 1000 + ms
                        lines.add(LyricLine(timeMs, text))
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return lines.sortedBy { it.timeMs }
}

fun downloadMusicItem(
    context: Context,
    item: MusicItem,
    musicList: SnapshotStateList<MusicItem>,
    client: OkHttpClient,
    scope: CoroutineScope
) {
    val index = musicList.indexOfFirst { it.id == item.id }
    if (index == -1) return
    musicList[index] = musicList[index].copy(downloadProgress = 0f)
    
    scope.launch(Dispatchers.IO) {
        try {
            val baseMusicUrl = "https://ghfast.top/https://raw.githubusercontent.com/shichen1234/music/main/"
            
            // 1. 下载 LRC 歌词文件
            val lrcUrl = "${baseMusicUrl}${Uri.encode(item.filename)}.lrc"
            val lrcRequest = Request.Builder().url(lrcUrl).build()
            client.newCall(lrcRequest).execute().use { response ->
                if (response.isSuccessful) {
                    writeTextToDownload(context, true, "${item.filename}.lrc", response.body?.string().orEmpty())
                }
            }
            
            // 2. 如果有翻译，下载翻译歌词文件
            if (item.hasTranslation) {
                val transUrl = "${baseMusicUrl}${Uri.encode(item.filename + "--翻译")}.lrc"
                val transRequest = Request.Builder().url(transUrl).build()
                client.newCall(transRequest).execute().use { response ->
                    if (response.isSuccessful) {
                        writeTextToDownload(context, true, "${item.filename}--翻译.lrc", response.body?.string().orEmpty())
                    }
                }
            }
            
            // 3. 下载 MP3 音乐文件
            val mp3Url = "${baseMusicUrl}${Uri.encode(item.filename)}.mp3"
            val mp3Request = Request.Builder().url(mp3Url).build()
            client.newCall(mp3Request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("HTTP ${response.code}")
                val body = response.body ?: throw Exception("Empty response body")
                val totalBytes = body.contentLength()
                var downloadedBytes = 0L
                var lastUpdate = 0L
                
                val outStream = getOutputStreamForDownload(context, true, "${item.filename}.mp3", "audio/mpeg")
                    ?: throw Exception("Failed to open output stream for music download")
                outStream.use { out ->
                    body.byteStream().use { inp ->
                        val buf = ByteArray(65536)
                        var n: Int
                        while (inp.read(buf).also { n = it } != -1) {
                            out.write(buf, 0, n)
                            downloadedBytes += n
                            val now = System.currentTimeMillis()
                            if (totalBytes > 0 && now - lastUpdate > 200) {
                                val progress = downloadedBytes.toFloat() / totalBytes
                                withContext(Dispatchers.Main) {
                                    val idx = musicList.indexOfFirst { it.id == item.id }
                                    if (idx != -1) {
                                        musicList[idx] = musicList[idx].copy(downloadProgress = progress)
                                    }
                                }
                                lastUpdate = now
                            }
                        }
                    }
                }
            }
            
            // 下载成功
            withContext(Dispatchers.Main) {
                val idx = musicList.indexOfFirst { it.id == item.id }
                if (idx != -1) {
                    musicList[idx] = musicList[idx].copy(
                        downloadProgress = 2f,
                        isDownloaded = true
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                val idx = musicList.indexOfFirst { it.id == item.id }
                if (idx != -1) {
                    musicList[idx] = musicList[idx].copy(downloadProgress = -1f)
                }
                android.widget.Toast.makeText(context, "下载失败: ${item.title}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}

suspend fun loadMusicList(context: Context): List<MusicItem> = withContext(Dispatchers.IO) {
    val list = mutableListOf<MusicItem>()
    val transSongIds = setOf(12, 21, 22, 23, 24, 26, 31, 35, 36, 37, 38, 40, 43, 44, 45, 46, 48, 50, 54, 55, 56, 58, 62, 66, 75, 76, 77, 78, 79, 84, 85, 86, 87, 92, 96, 98, 100, 32, 109, 115, 116, 117, 118, 119)
    
    val uriString = getMusicDownloadUri(context)
    val existingFiles = mutableSetOf<String>()
    
    if (uriString.isNullOrBlank()) {
        val musicDir = File(context.filesDir, "music")
        if (musicDir.exists() && musicDir.isDirectory) {
            musicDir.list()?.forEach { existingFiles.add(it) }
        }
    } else {
        try {
            val treeUri = Uri.parse(uriString)
            val documentDir = DocumentFile.fromTreeUri(context, treeUri)
            if (documentDir != null && documentDir.exists()) {
                documentDir.listFiles().forEach { file ->
                    val name = file.name
                    if (name != null) {
                        existingFiles.add(name)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    try {
        context.assets.open("1.txt").bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val idMatch = """id:\s*(\d+)""".toRegex().find(line)
                val filenameMatch = """filename:\s*"([^"]+)"""".toRegex().find(line)
                if (idMatch != null && filenameMatch != null) {
                    val id = idMatch.groupValues[1].toInt()
                    val filename = filenameMatch.groupValues[1]
                    val parts = filename.split("--")
                    val title = parts.getOrNull(1) ?: ""
                    val artist = parts.getOrNull(2) ?: ""
                    val isDownloaded = existingFiles.contains("$filename.mp3")
                    list.add(
                        MusicItem(
                            id = id,
                            filename = filename,
                            title = title,
                            artist = artist,
                            downloadProgress = if (isDownloaded) 2f else -1f,
                            isDownloaded = isDownloaded,
                            hasTranslation = transSongIds.contains(id)
                        )
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    list
}


/** 下载壁纸并回报进度 [0f‥1f]，返回本地绝对路径，失败返回 null */
suspend fun downloadWallpaper(
    context: Context,
    item: WallpaperItem,
    client: OkHttpClient,
    onProgress: (Float) -> Unit
): String? = withContext(Dispatchers.IO) {
    try {
        val dir = File(context.filesDir, "wallpapers/${item.type}").also { it.mkdirs() }
        val file = File(dir, item.fileName)
        val req = Request.Builder().url(item.downloadUrl).build()
        val resp = client.newCall(req).execute()
        if (!resp.isSuccessful) throw Exception("HTTP ${resp.code}")
        val body = resp.body ?: throw Exception("Empty response body")
        val total = body.contentLength().toFloat()
        var received = 0L
        var lastUpdate = 0L
        
        FileOutputStream(file).use { out ->
            body.byteStream().use { inp ->
                val buf = ByteArray(65536) // 64KB 缓冲区减少 IO 频率
                var n: Int
                while (inp.read(buf).also { n = it } != -1) {
                    out.write(buf, 0, n)
                    received += n
                    val now = System.currentTimeMillis()
                    // 限制进度更新频率 (每 100ms 最多一次)，避免过度消耗 UI 性能
                    if (total > 0 && now - lastUpdate > 100) {
                        withContext(Dispatchers.Main) { onProgress(received / total) }
                        lastUpdate = now
                    }
                }
            }
        }
        // 最后强制更新一次 100%
        withContext(Dispatchers.Main) { onProgress(1f) }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            android.widget.Toast.makeText(context, "下载失败: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
        }
        null
    }
}

private const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_progress"

private fun formatDownloadBytes(bytes: Long): String {
    val safeBytes = bytes.coerceAtLeast(0L)
    return when {
        safeBytes >= 1024L * 1024L * 1024L -> String.format(Locale.getDefault(), "%.2f GB", safeBytes / (1024f * 1024f * 1024f))
        safeBytes >= 1024L * 1024L -> String.format(Locale.getDefault(), "%.2f MB", safeBytes / (1024f * 1024f))
        safeBytes >= 1024L -> String.format(Locale.getDefault(), "%.1f KB", safeBytes / 1024f)
        else -> "$safeBytes B"
    }
}private fun formatDownloadSpeed(bytesPerSec: Long): String = "${formatDownloadBytes(bytesPerSec)}/s"

private fun downloadNotificationId(downloadId: Long): Int {
    val id = (downloadId % Int.MAX_VALUE).toInt()
    return if (id > 0) id else 1
}

private fun canPostDownloadNotifications(context: Context): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
    ) {
        return false
    }
    return NotificationManagerCompat.from(context).areNotificationsEnabled()
}

private fun ensureDownloadNotificationChannel(context: Context) {
    val channel = NotificationChannel(
        DOWNLOAD_NOTIFICATION_CHANNEL_ID,
        "下载进度",
        NotificationManager.IMPORTANCE_LOW
    )
    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(channel)
}// 全局下载协程 Job 管理器，用于取消正在进行的 OkHttp 下载
val activeDownloadJobs = ConcurrentHashMap<Long, Job>()

private fun downloadProgressText(
    downloadedBytes: Long,
    totalBytes: Long,
    statusOrSpeed: String
): String {
    val sizeText = if (totalBytes > 0L) {
        "${formatDownloadBytes(downloadedBytes)} / ${formatDownloadBytes(totalBytes)}"
    } else {
        formatDownloadBytes(downloadedBytes)
    }
    return if (statusOrSpeed.isBlank()) sizeText else "$sizeText · $statusOrSpeed"
}

private fun showDownloadNotification(
    context: Context,
    item: DownloadItem,
    downloadedBytes: Long,
    totalBytes: Long,
    statusOrSpeed: String,
    finished: Boolean = false,
    failed: Boolean = false
) {
    if (item.downloadId == -1L || !canPostDownloadNotifications(context)) return

    ensureDownloadNotificationChannel(context)
    val progress = if (totalBytes > 0L) {
        ((downloadedBytes * 100L) / totalBytes).coerceIn(0L, 100L).toInt()
    } else {
        0
    }
    val contentText = when {
        finished -> "下载完成 · ${formatDownloadBytes(downloadedBytes)}"
        failed -> statusOrSpeed.ifBlank { "下载失败" }
        else -> downloadProgressText(downloadedBytes, totalBytes, statusOrSpeed.ifBlank { "等待下载" })
    }

    // 点击通知跳转到下载页面
    val tapIntent = Intent(context, MainActivity::class.java).apply {
        action = "OPEN_DOWNLOADS"
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, tapIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(item.fileName)
        .setContentText(contentText)
        .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
        .setContentIntent(pendingIntent)
        .setOnlyAlertOnce(true)
        .setOngoing(!finished && !failed)
        .setAutoCancel(finished || failed)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setProgress(100, progress, !finished && !failed && totalBytes <= 0L)
        .build()

    try {
        NotificationManagerCompat.from(context).notify(downloadNotificationId(item.downloadId), notification)
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}fun cancelDownloadNotification(context: Context, downloadId: Long) {
    if (downloadId != -1L) {
        NotificationManagerCompat.from(context).cancel(downloadNotificationId(downloadId))
    }
}/** 获取下载文件的路径 */
fun getDownloadFilePath(fileName: String): File {
    val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
        android.os.Environment.DIRECTORY_DOWNLOADS
    )
    return File(downloadsDir, fileName)
}

/** 打开已下载的文件 */
fun openDownloadedFile(context: Context, fileName: String) {
    try {
        val uri = getDownloadFileUri(context, fileName)
        if (uri == null) {
            android.widget.Toast.makeText(context, "文件不存在", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        // APK 文件特殊处理：使用安装流程
        if (fileName.endsWith(".apk", ignoreCase = true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !context.packageManager.canRequestPackageInstalls()) {
                // 引导用户去设置中开启权限
                val settingsIntent = Intent(
                    android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    Uri.parse("package:${context.packageName}")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(settingsIntent)
                android.widget.Toast.makeText(context, "请先允许安装未知来源应用", android.widget.Toast.LENGTH_LONG).show()
                return
            }
            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(installIntent)
            return
        }

        val ext = fileName.substringAfterLast('.', "")
        val mimeType = android.webkit.MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(ext.lowercase()) ?: "*/*"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        android.widget.Toast.makeText(context, "无法打开文件: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
    }
}/** 暂停下载：取消协程但保留已下载的临时文件和进度 */
fun pauseDownload(
    downloadId: Long,
    downloadsList: MutableList<DownloadItem>
) {
    activeDownloadJobs[downloadId]?.cancel()
    activeDownloadJobs.remove(downloadId)
    val index = downloadsList.indexOfFirst { it.downloadId == downloadId }
    if (index != -1) {
        downloadsList[index] = downloadsList[index].copy(
            status = "Paused",
            speed = "已暂停"
        )
    }
}/** OkHttp 协程下载的核心逻辑，支持 Range 断点续传 */
private fun launchDownloadJob(
    context: Context,
    item: DownloadItem,
    downloadId: Long,
    downloadsList: MutableList<DownloadItem>,
    scope: CoroutineScope,
    client: OkHttpClient,
    resumeFromBytes: Long = 0L
) {
    val job = scope.launch(Dispatchers.IO) {
        try {
            val reqBuilder = Request.Builder().url(item.url)
            if (item.savedUserAgent.isNotBlank()) reqBuilder.addHeader("User-Agent", item.savedUserAgent)
            if (item.savedReferer.isNotBlank()) reqBuilder.addHeader("Referer", item.savedReferer)
            CookieManager.getInstance().getCookie(item.url)?.takeIf { it.isNotBlank() }?.let { cookies ->
                reqBuilder.addHeader("Cookie", cookies)
            }
            // 断点续传：发送 Range 头
            if (resumeFromBytes > 0L) {
                reqBuilder.addHeader("Range", "bytes=$resumeFromBytes-")
            }

            val response = client.newCall(reqBuilder.build()).execute()
            if (!response.isSuccessful && response.code != 206) {
                throw Exception("HTTP ${response.code}")
            }
            val body = response.body
            val contentLength = body.contentLength()
            // 如果服务器返回 206，总大小 = 已下载 + 剩余
            val totalBytes = if (response.code == 206 && contentLength > 0) {
                resumeFromBytes + contentLength
            } else if (contentLength > 0) {
                contentLength
            } else {
                item.totalBytes // 保留之前已知的总大小
            }

            val mimeType = when (item.fileName.substringAfterLast('.', "").lowercase()) {
                "apk" -> "application/vnd.android.package-archive"
                "mp4" -> "video/mp4"
                "jpg", "jpeg" -> "image/jpeg"
                "png" -> "image/png"
                "mp3" -> "audio/mpeg"
                "pdf" -> "application/pdf"
                "txt" -> "text/plain"
                else -> "application/octet-stream"
            }
            val outStream = getOutputStreamForDownload(context, false, item.fileName, mimeType, resumeFromBytes > 0L)
                ?: throw Exception("Failed to open output stream for file download")

            var downloadedBytes = resumeFromBytes
            var lastUpdateTime = System.currentTimeMillis()
            var lastUpdateBytes = downloadedBytes

            outStream.use { out ->
                body.byteStream().use { inp ->
                    val buf = ByteArray(8192)
                    var n: Int
                    while (inp.read(buf).also { n = it } != -1) {
                        out.write(buf, 0, n)
                        downloadedBytes += n

                        val now = System.currentTimeMillis()
                        if (now - lastUpdateTime >= 500) {
                            val bytesDiff = (downloadedBytes - lastUpdateBytes).coerceAtLeast(0L)
                            val timeDiff = now - lastUpdateTime
                            val speedStr = if (timeDiff > 0 && bytesDiff > 0) {
                                formatDownloadSpeed((bytesDiff * 1000) / timeDiff)
                            } else "0 B/s"
                            val progress = if (totalBytes > 0) downloadedBytes.toFloat() / totalBytes else 0f
                            val currentDownloaded = downloadedBytes
                            lastUpdateTime = now
                            lastUpdateBytes = downloadedBytes

                            withContext(Dispatchers.Main) {
                                val index = downloadsList.indexOfFirst { it.downloadId == downloadId }
                                if (index != -1) {
                                    downloadsList[index] = downloadsList[index].copy(
                                        progress = progress.coerceIn(0f, 1f),
                                        speed = speedStr,
                                        downloadedBytes = currentDownloaded,
                                        totalBytes = totalBytes
                                    )
                                }
                                showDownloadNotification(
                                    context = context,
                                    item = item.copy(downloadId = downloadId),
                                    downloadedBytes = currentDownloaded,
                                    totalBytes = totalBytes,
                                    statusOrSpeed = speedStr
                                )
                            }
                        }
                    }
                }
            }

            if (getFileDownloadUri(context).isNullOrBlank()) {
                val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
                    android.os.Environment.DIRECTORY_DOWNLOADS
                )
                val targetFile = File(downloadsDir, item.fileName)
                MediaScannerConnection.scanFile(context, arrayOf(targetFile.absolutePath), null, null)
            }

            withContext(Dispatchers.Main) {
                val index = downloadsList.indexOfFirst { it.downloadId == downloadId }
                if (index != -1) {
                    downloadsList[index] = downloadsList[index].copy(
                        status = "Completed",
                        progress = 1f,
                        speed = "",
                        downloadedBytes = downloadedBytes,
                        totalBytes = if (totalBytes > 0L) totalBytes else downloadedBytes
                    )
                }
                showDownloadNotification(
                    context = context,
                    item = item.copy(downloadId = downloadId),
                    downloadedBytes = downloadedBytes,
                    totalBytes = if (totalBytes > 0L) totalBytes else downloadedBytes,
                    statusOrSpeed = "",
                    finished = true
                )
            }
        } catch (_: CancellationException) {
            // 暂停或取消 — 不改状态，由调用方决定
            // 如果是暂停，pauseDownload 已经设置了 Paused 状态
            // 如果当前已经是 Paused 就保持，否则标记 Failed
            withContext(Dispatchers.Main) {
                val index = downloadsList.indexOfFirst { it.downloadId == downloadId }
                if (index != -1 && downloadsList[index].status != "Paused") {
                    downloadsList[index] = downloadsList[index].copy(
                        status = "Failed", speed = "已取消"
                    )
                    cancelDownloadNotification(context, downloadId)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("DownloadTask", "Download failed", e)
            withContext(Dispatchers.Main) {
                val index = downloadsList.indexOfFirst { it.downloadId == downloadId }
                if (index != -1) {
                    downloadsList[index] = downloadsList[index].copy(
                        status = "Failed", speed = e.message ?: "下载失败"
                    )
                }
                showDownloadNotification(
                    context = context,
                    item = item.copy(downloadId = downloadId),
                    downloadedBytes = 0L,
                    totalBytes = -1L,
                    statusOrSpeed = e.message ?: "下载失败",
                    failed = true
                )
            }
        } finally {
            activeDownloadJobs.remove(downloadId)
        }
    }
    activeDownloadJobs[downloadId] = job
}

/** 使用 OkHttp 协程直接下载文件，不依赖系统 DownloadManager */
fun startDownloadTask(
    context: Context,
    item: DownloadItem,
    userAgent: String,
    referer: String,
    downloadsList: MutableList<DownloadItem>,
    scope: CoroutineScope,
    client: OkHttpClient,
    onIdAssigned: (Long) -> Unit
) {
    val downloadId = System.currentTimeMillis()
    onIdAssigned(downloadId)
    android.util.Log.d("DownloadTask", "Starting OkHttp download: ${item.url}")
    android.widget.Toast.makeText(context, "已开始下载: ${item.fileName}", android.widget.Toast.LENGTH_SHORT).show()

    showDownloadNotification(
        context = context,
        item = item.copy(downloadId = downloadId),
        downloadedBytes = 0L,
        totalBytes = -1L,
        statusOrSpeed = "连接中…"
    )

    // 保存请求参数到 item，以便暂停后恢复时使用
    val idx = downloadsList.indexOfFirst { it.id == item.id }
    if (idx != -1) {
        downloadsList[idx] = downloadsList[idx].copy(
            savedUserAgent = userAgent,
            savedReferer = referer
        )
    }

    launchDownloadJob(
        context = context,
        item = item.copy(downloadId = downloadId, savedUserAgent = userAgent, savedReferer = referer),
        downloadId = downloadId,
        downloadsList = downloadsList,
        scope = scope,
        client = client
    )
}

/** 恢复已暂停的下载 */
fun resumeDownloadTask(
    context: Context,
    item: DownloadItem,
    downloadsList: MutableList<DownloadItem>,
    scope: CoroutineScope,
    client: OkHttpClient
) {
    val index = downloadsList.indexOfFirst { it.downloadId == item.downloadId }
    if (index != -1) {
        downloadsList[index] = downloadsList[index].copy(
            status = "Downloading",
            speed = "恢复中…"
        )
    }

    showDownloadNotification(
        context = context,
        item = item,
        downloadedBytes = item.downloadedBytes,
        totalBytes = item.totalBytes,
        statusOrSpeed = "恢复中…"
    )

    launchDownloadJob(
        context = context,
        item = item,
        downloadId = item.downloadId,
        downloadsList = downloadsList,
        scope = scope,
        client = client,
        resumeFromBytes = item.downloadedBytes
    )
}

class MainActivity : ComponentActivity() {
    // 性能优化：懒加载重量级对象，并优化 OkHttp 连接池
    val client by lazy { 
        OkHttpClient.Builder()
            .connectionPool(okhttp3.ConnectionPool(5, 5, java.util.concurrent.TimeUnit.MINUTES))
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build() 
    }
    val gson by lazy { Gson() }

    // 用于通知栏点击跳转到下载页面
    var pendingOpenDownloads = mutableStateOf(false)

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == "OPEN_DOWNLOADS") {
            pendingOpenDownloads.value = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 检查启动 intent 是否需要打开下载页面
        if (intent?.action == "OPEN_DOWNLOADS") {
            pendingOpenDownloads.value = true
        }
        @Suppress("DEPRECATION")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        setContent {
            val context = LocalContext.current
            
            // 请求通知权限 (Android 13+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { _ -> }
                LaunchedEffect(Unit) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) 
                        != PackageManager.PERMISSION_GRANTED) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            MyApplicationTheme {
                val context = LocalContext.current
                val exoPlayer = remember {
                    ExoPlayer.Builder(context).build().apply {
                        repeatMode = Player.REPEAT_MODE_ALL
                        volume = 0f
                        playWhenReady = true
                    }
                }
                DisposableEffect(Unit) {
                    onDispose { exoPlayer.release() }
                }

                // 全局生命周期管理：App 退到后台时暂停视频，回到前台时恢复
                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                DisposableEffect(lifecycleOwner) {
                    val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
                        when (event) {
                            androidx.lifecycle.Lifecycle.Event.ON_STOP -> {
                                exoPlayer.playWhenReady = false
                            }
                            androidx.lifecycle.Lifecycle.Event.ON_START -> {
                                exoPlayer.playWhenReady = true
                            }
                            else -> {}
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                // ── 全局部分初始化与服务绑定 ──
                val musicList = remember { mutableStateListOf<MusicItem>() }
                var currentPlayingSong by remember { mutableStateOf<MusicItem?>(null) }
                var isMusicPlaying by remember { mutableStateOf(false) }
                var musicPosition by remember { mutableLongStateOf(0L) }
                var musicDuration by remember { mutableLongStateOf(0L) }
                var showMusicPlayer by remember { mutableStateOf(false) }
                val lyricLines = remember { mutableStateListOf<LyricLine>() }
                val translationLines = remember { mutableStateListOf<LyricLine>() }
                val musicPrefs = remember { context.getSharedPreferences("music_prefs", Context.MODE_PRIVATE) }

                var musicService by remember { mutableStateOf<MusicService?>(null) }
                var isMusicBound by remember { mutableStateOf(false) }

                val serviceConnection = remember {
                    object : android.content.ServiceConnection {
                        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                            val binder = service as? MusicService.MusicBinder
                            musicService = binder?.getService()
                            isMusicBound = true
                        }
                        override fun onServiceDisconnected(name: ComponentName?) {
                            musicService = null
                            isMusicBound = false
                        }
                    }
                }

                DisposableEffect(context) {
                    val intent = Intent(context, MusicService::class.java)
                    context.startService(intent)
                    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                    onDispose {
                        if (isMusicBound) {
                            context.unbindService(serviceConnection)
                            isMusicBound = false
                        }
                    }
                }

                LaunchedEffect(musicService) {
                    val service = musicService ?: return@LaunchedEffect
                    currentPlayingSong = service.currentSong
                    isMusicPlaying = service.musicPlayer.isPlaying
                    musicDuration = service.musicPlayer.duration.coerceAtLeast(0L)
                    musicPosition = service.musicPlayer.currentPosition
                    
                    service.onSongChangedListener = { song ->
                        currentPlayingSong = song
                    }
                    service.onPlayStateChangedListener = { playing ->
                        isMusicPlaying = playing
                    }
                    
                    val listener = object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            if (playbackState == Player.STATE_READY) {
                                musicDuration = service.musicPlayer.duration.coerceAtLeast(0L)
                            }
                        }
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            isMusicPlaying = isPlaying
                            if (isPlaying) {
                                musicDuration = service.musicPlayer.duration.coerceAtLeast(0L)
                            }
                        }
                    }
                    service.musicPlayer.addListener(listener)
                    
                    try {
                        while (true) {
                            if (service.musicPlayer.isPlaying) {
                                musicPosition = service.musicPlayer.currentPosition
                                musicDuration = service.musicPlayer.duration.coerceAtLeast(0L)
                            }
                            delay(500)
                        }
                    } finally {
                        service.musicPlayer.removeListener(listener)
                    }
                }

                // 启动时读取 assets 中的歌曲列表
                LaunchedEffect(Unit) {
                    val list = loadMusicList(context)
                    musicList.clear()
                    musicList.addAll(list)
                }

                // 监听当前歌曲更改，解析歌词
                LaunchedEffect(currentPlayingSong) {
                    lyricLines.clear()
                    translationLines.clear()
                    val song = currentPlayingSong
                    if (song != null) {
                        lyricLines.addAll(getMusicLyricLines(context, song.filename, false))
                        if (song.hasTranslation) {
                            translationLines.addAll(getMusicLyricLines(context, song.filename, true))
                        }
                    }
                }

                // 恢复上次关闭时的歌曲播放进度
                LaunchedEffect(musicService, musicList.size) {
                    val service = musicService ?: return@LaunchedEffect
                    if (musicList.isEmpty()) return@LaunchedEffect
                    if (service.currentSong == null) {
                        val savedSongId = musicPrefs.getInt("last_song_id", -1)
                        if (savedSongId != -1) {
                            val savedSong = musicList.find { it.id == savedSongId }
                            if (savedSong != null) {
                                val savedPos = musicPrefs.getLong("last_position", 0L)
                                service.restoreSong(savedSong, musicList, savedPos)
                                currentPlayingSong = savedSong
                                musicPosition = savedPos
                                // Retrieve duration if prepared
                                musicDuration = service.musicPlayer.duration.coerceAtLeast(0L)
                            }
                        }
                    }
                }

                // 定时及手动定位时，持续将进度写入 musicPrefs
                LaunchedEffect(currentPlayingSong, musicPosition) {
                    if (currentPlayingSong != null) {
                        musicPrefs.edit()
                            .putInt("last_song_id", currentPlayingSong!!.id)
                            .putLong("last_position", musicPosition)
                            .apply()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent, // 强制 Scaffold 背景透明
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                    // ── 全局状态 ──
                    var tabs by remember { mutableStateOf(listOf<String?>("about:blank")) }
                    var currentTabIndex by remember { mutableIntStateOf(0) }
                    val tabWebViews = remember { mutableStateListOf<WebView>() }
                    // 网页加载进度
                    var webLoadingProgress by remember { mutableFloatStateOf(0f) }
                    val context = LocalContext.current

                    // 壁纸状态
                    val wallpaperPrefs = remember { context.getSharedPreferences("wallpaper_prefs", MODE_PRIVATE) }
                    var wallpaperType by remember { mutableStateOf(wallpaperPrefs.getString("type", "static") ?: "static") }
                    var wallpaperPath by remember { mutableStateOf(wallpaperPrefs.getString("path", "asset:///fengmian.png") ?: "asset:///fengmian.png") }
                    var showWallpaperPicker by remember { mutableStateOf(false) }
                    
                    // 启动加载状态
                    var isBackgroundReady by remember { mutableStateOf(false) }
                    var isWallpaperLoaded by remember { mutableStateOf(false) }
                    
                    
                    val downloadProgress = remember { mutableStateMapOf<String, Float>() }
                    val downloadedPaths = remember { mutableStateMapOf<String, String>() }

                    // 历史记录与书签状态
                    val browserPrefs = remember { context.getSharedPreferences("browser_data", Context.MODE_PRIVATE) }
                    val historyList = remember { 
                        val json = browserPrefs.getString("history", "[]")
                        val type = object : TypeToken<MutableList<HistoryItem>>() {}.type
                        mutableStateListOf<HistoryItem>().apply { 
                            addAll(gson.fromJson<MutableList<HistoryItem>>(json, type) ?: mutableListOf()) 
                        }
                    }
                    val bookmarksList = remember {
                        val json = browserPrefs.getString("bookmarks", "[]")
                        val type = object : TypeToken<MutableList<BookmarkItem>>() {}.type
                        mutableStateListOf<BookmarkItem>().apply {
                            addAll(gson.fromJson<MutableList<BookmarkItem>>(json, type) ?: mutableListOf())
                        }
                    }
                    val downloadsList = remember {
                        val json = browserPrefs.getString("downloads", "[]")
                        val type = object : TypeToken<MutableList<DownloadItem>>() {}.type
                        mutableStateListOf<DownloadItem>().apply {
                            addAll(gson.fromJson<MutableList<DownloadItem>>(json, type) ?: mutableListOf())
                        }
                    }

                    var showHistory by remember { mutableStateOf(false) }
                    var showBookmarks by remember { mutableStateOf(false) }
                    var showDownloads by remember { mutableStateOf(false) }
                    var showSettings by remember { mutableStateOf(false) }
                    var showDownloadSettings by remember { mutableStateOf(false) }
                    var isNightMode by remember {
                        mutableStateOf(browserPrefs.getBoolean("night_mode", false))
                    }

                    val isWhiteIcon = remember(wallpaperType, wallpaperPath, isNightMode) {
                        (wallpaperType == "static" && (
                                wallpaperPath.endsWith("static_2.jpg") || 
                                wallpaperPath.endsWith("static_5.jpg") ||
                                wallpaperPath.endsWith("static_7.jpg") ||
                                wallpaperPath.endsWith("static_9.jpg") ||
                                wallpaperPath.endsWith("static_11.jpg") ||
                                wallpaperPath.endsWith("static_12.jpg")
                        )) ||
                        (wallpaperType == "video" && (
                                wallpaperPath.endsWith("video_1.mp4") || 
                                wallpaperPath.endsWith("video_4.mp4") ||
                                wallpaperPath.endsWith("video_8.mp4")
                        ))
                    }
                    
                    // 只有当壁纸加载完成且一定时间过去后，才隐藏开屏。
                    // 隐藏开屏后，将 Activity 窗口背景颜色修改为单色，避免后续因壁纸加载/转场时露出系统默认的开屏背景
                    LaunchedEffect(isWallpaperLoaded, isNightMode) {
                        if (isWallpaperLoaded) {
                            (context as? android.app.Activity)?.window?.setBackgroundDrawable(
                                android.graphics.drawable.ColorDrawable(
                                    if (isNightMode) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                                )
                            )
                        }
                    }
                    LaunchedEffect(Unit) {
                        delay(150)
                        isBackgroundReady = true
                    }
                    var isDesktopMode by remember { mutableStateOf(false) }
                    // 收藏弹窗状态
                    var showBookmarkDialog by remember { mutableStateOf(false) }
                    var pendingBookmarkTitle by remember { mutableStateOf("") }
                    var pendingBookmarkUrl by remember { mutableStateOf("") }
                    // 字体大小
                    var webFontSize by remember {
                        mutableStateOf(browserPrefs.getInt("font_size", 100))
                    }

                    // --- Cookies 状态 ---
                    var allowCookies by remember {
                        mutableStateOf(browserPrefs.getBoolean("allow_cookies", true))
                    }
                    val cookieDomains = remember {
                        val json = browserPrefs.getString("cookie_domains", "[]")
                        val type = object : TypeToken<MutableSet<String>>() {}.type
                        mutableStateListOf<String>().apply {
                            addAll(gson.fromJson<MutableSet<String>>(json, type) ?: mutableSetOf())
                        }
                    }
                    var showCookiesSettings by remember { mutableStateOf(false) }

                    // --- 全屏视频状态 ---
                    var customView by remember { mutableStateOf<android.view.View?>(null) }
                    var customViewCallback by remember { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }

                    // 保存 Cookies 域名列表
                    LaunchedEffect(cookieDomains.toList()) {
                        withContext(Dispatchers.IO) {
                            val json = gson.toJson(cookieDomains.toSet())
                            browserPrefs.edit().putString("cookie_domains", json).apply()
                        }
                    }

                    // 监听通知栏跳转请求
                    val activity = context as? MainActivity
                    LaunchedEffect(activity?.pendingOpenDownloads?.value) {
                        if (activity?.pendingOpenDownloads?.value == true) {
                            showDownloads = true
                            activity.pendingOpenDownloads.value = false
                        }
                    }

                    // 下载对话框状态
                    var showDownloadDialog by remember { mutableStateOf(false) }
                    var pendingDownloadUrl by remember { mutableStateOf("") }
                    var pendingDownloadName by remember { mutableStateOf("") }
                    var pendingDownloadSize by remember { mutableStateOf("") }
                    var pendingDownloadUserAgent by remember { mutableStateOf("") }
                    var pendingDownloadReferer by remember { mutableStateOf("") }

                    // 保存数据到本地（在 IO 线程序列化，避免阻塞主线程动画）
                    LaunchedEffect(historyList.size) {
                        withContext(Dispatchers.IO) {
                            val json = gson.toJson(historyList.toList())
                            browserPrefs.edit().putString("history", json).apply()
                        }
                    }
                    LaunchedEffect(bookmarksList.size) {
                        withContext(Dispatchers.IO) {
                            val json = gson.toJson(bookmarksList.toList())
                            browserPrefs.edit().putString("bookmarks", json).apply()
                        }
                    }
                    // 性能优化：防抖保存下载列表，避免每次进度更新都序列化 JSON
                    LaunchedEffect(downloadsList.toList()) {
                        delay(2000) // 防抖 2 秒
                        withContext(Dispatchers.IO) {
                            val json = gson.toJson(downloadsList.toList())
                            browserPrefs.edit().putString("downloads", json).apply()
                        }
                    }

                    // --- 下载协程作用域 ---
                    val downloadScope = rememberCoroutineScope()
                    val okHttpClient = remember { (context as? MainActivity)?.client ?: OkHttpClient() }

                    val musicFolderLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.OpenDocumentTree()
                    ) { uri ->
                        if (uri != null) {
                            val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            try {
                                context.contentResolver.takePersistableUriPermission(uri, takeFlags)
                                context.getSharedPreferences("download_settings", Context.MODE_PRIVATE)
                                    .edit()
                                    .putString("music_download_uri", uri.toString())
                                    .apply()
                                downloadScope.launch {
                                    val list = loadMusicList(context)
                                    musicList.clear()
                                    musicList.addAll(list)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    val fileFolderLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.OpenDocumentTree()
                    ) { uri ->
                        if (uri != null) {
                            val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            try {
                                context.contentResolver.takePersistableUriPermission(uri, takeFlags)
                                context.getSharedPreferences("download_settings", Context.MODE_PRIVATE)
                                    .edit()
                                    .putString("file_download_uri", uri.toString())
                                    .apply()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    // 性能优化：将 IO 密集型扫描移至后台，且使用 side-effect 避免阻塞首屏
                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                            val results = mutableMapOf<String, String>()
                            (videoWallpapers + staticWallpapers).forEach { item ->
                                val f = File(context.filesDir, "wallpapers/${item.type}/${item.fileName}")
                                if (f.exists()) results["${item.type}_${item.id}"] = f.absolutePath
                            }
                            withContext(Dispatchers.Main) {
                                downloadedPaths.putAll(results)
                            }
                        }
                    }

                    // 长按主页时缩放
                    val homeScaleAnim = remember { Animatable(1f) }
                    LaunchedEffect(showWallpaperPicker) {
                        homeScaleAnim.animateTo(
                            if (showWallpaperPicker) 0.88f else 1f,
                            tween(300, easing = FastOutSlowInEasing)
                        )
                    }

                    // 删除最后一个 tab 再重置时强制递增，让 key(tabs, tabsResetVersion) 感知到变化
                    var tabsResetVersion by remember { mutableStateOf(0) }
                    var showMenu by remember { mutableStateOf(false) }
                    var isSearchFocused by remember { mutableStateOf(false) }
                    var showTabOverview by remember { mutableStateOf(false) }
                    var webViewRef by remember { mutableStateOf<WebView?>(null) }
                    val focusManager = LocalFocusManager.current

                    // Apply Night Mode via Android forceDark API (like Edge browser)
                    // 不使用 CSS filter 反色，而是用系统级深色模式，只改背景色不改图片
                    LaunchedEffect(isNightMode, currentTabIndex, tabWebViews.size) {
                        val webView = tabWebViews.getOrNull(currentTabIndex)
                        if (webView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            @Suppress("DEPRECATION")
                            webView.settings.forceDark = if (isNightMode)
                                android.webkit.WebSettings.FORCE_DARK_ON
                            else
                                android.webkit.WebSettings.FORCE_DARK_OFF
                        }
                    }

                    // Keep WebViews in sync with tabs
                    // tabsResetVersion 也作为 key：删除最后一个 tab 重置后 size 不变(1→1)，
                    // 加入 version 保证 LaunchedEffect                    // 性能优化：延迟创建 WebView，仅在需要时分配资源
                    LaunchedEffect(tabs.size, tabsResetVersion) {
                        while (tabWebViews.size < tabs.size) {
                            val newWebView = WebView(context).apply {
                                settings.apply {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    // 开启硬件加速
                                    setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)
                                    // 允许自适应屏幕
                                    useWideViewPort = true
                                    loadWithOverviewMode = true
                                    // 某些视频网站需要 databaseEnabled
                                    databaseEnabled = true

                                    // 核心修复：修改 User-Agent。
                                    if (isDesktopMode) {
                                        userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
                                    } else {
                                        userAgentString = userAgentString.replace("; wv", "").replace("Version/4.0 ", "")
                                    }
                                    textZoom = webFontSize
                                    
                                    // 允许选择文字 and 长按复制
                                    isLongClickable = true

                                    // 双指缩放
                                    setSupportZoom(isDesktopMode)
                                    builtInZoomControls = isDesktopMode
                                    displayZoomControls = false

                                    // 夜间模式：使用系统级 forceDark（类似 Edge 深色模式）
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        @Suppress("DEPRECATION")
                                        forceDark = if (isNightMode)
                                            android.webkit.WebSettings.FORCE_DARK_ON
                                        else
                                            android.webkit.WebSettings.FORCE_DARK_OFF
                                    }
                                }
                                
                                // 配置 CookieManager
                                CookieManager.getInstance().setAcceptCookie(allowCookies)
                                CookieManager.getInstance().setAcceptThirdPartyCookies(this, allowCookies)

                                webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        super.onPageFinished(view, url)
                                        if (view?.getTag() == true) {
                                            view.setTag(null)
                                            view.clearHistory()
                                        }
                                        if (url != null && !url.startsWith("file://") && url != "about:blank" && view?.title != null) {
                                            val newItem = HistoryItem(view.title ?: url, url, System.currentTimeMillis())
                                            // 避免重复记录同一页面（简单去重）
                                            if (historyList.firstOrNull()?.url != url) {
                                                historyList.add(0, newItem)
                                                if (historyList.size > 100) historyList.removeAt(historyList.size - 1)
                                            }
                                            
                                            // 记录 Cookie 域名
                                            val domain = Uri.parse(url).host
                                            if (domain != null && allowCookies) {
                                                val cookies = CookieManager.getInstance().getCookie(url)
                                                if (!cookies.isNullOrBlank() && !cookieDomains.contains(domain)) {
                                                    cookieDomains.add(domain)
                                                }
                                            }
                                            CookieManager.getInstance().flush()
                                        }
                                        if (isNightMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            // forceDark 需要页面加载后重新确认
                                            @Suppress("DEPRECATION")
                                            view?.settings?.forceDark = android.webkit.WebSettings.FORCE_DARK_ON
                                        }
                                    }

                                    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                                        super.doUpdateVisitedHistory(view, url, isReload)
                                        if (url != null) {
                                            // 同步 tabs 状态
                                            val index = tabWebViews.indexOf(view)
                                            if (index != -1 && tabs.getOrNull(index) != url) {
                                                tabs = tabs.toMutableList().also { it[index] = url }
                                            }

                                            if (!url.startsWith("file://") && url != "about:blank") {
                                                // 使用 URL 变化即触发记录，标题若未加载完则用 URL 占位
                                                val currentTitle = view?.title ?: url
                                                if (historyList.firstOrNull()?.url != url) {
                                                    historyList.add(0, HistoryItem(currentTitle, url, System.currentTimeMillis()))
                                                    if (historyList.size > 100) historyList.removeAt(historyList.size - 1)
                                                }
                                            }
                                        }
                                    }

                                    override fun shouldOverrideUrlLoading(
                                        view: WebView,
                                        request: WebResourceRequest
                                    ): Boolean {
                                        val requestUrl = request.url.toString()
                                        if (requestUrl.startsWith("http://") || requestUrl.startsWith("https://")) {
                                            return false
                                        }
                                        try {
                                            val intent = android.content.Intent.parseUri(requestUrl, android.content.Intent.URI_INTENT_SCHEME)
                                            if (intent != null) {
                                                context.startActivity(intent)
                                                return true
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                        return true
                                    }
                                }
                                webChromeClient = object : WebChromeClient() {
                                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                        if (tabWebViews.indexOf(view) == currentTabIndex) {
                                            webLoadingProgress = newProgress / 100f
                                        }
                                    }

                                    // 支持全屏视频
                                    override fun onShowCustomView(view: android.view.View?, callback: CustomViewCallback?) {
                                        customView = view
                                        customViewCallback = callback
                                        val activity = context as? ComponentActivity
                                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                    }

                                    override fun onHideCustomView() {
                                        customViewCallback?.onCustomViewHidden()
                                        customView = null
                                        customViewCallback = null
                                        val activity = context as? ComponentActivity
                                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                    }
                                }
                                setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                                    pendingDownloadUrl = url
                                    pendingDownloadName = android.webkit.URLUtil.guessFileName(url, contentDisposition, mimetype)
                                    pendingDownloadSize = if (contentLength > 0) {
                                        val kb = contentLength / 1024
                                        if (kb > 1024) String.format(java.util.Locale.getDefault(), "%.2f MB", kb / 1024f) else "$kb KB"
                                    } else "Unknown"
                                    pendingDownloadUserAgent = userAgent.orEmpty()
                                    pendingDownloadReferer = this.url.orEmpty()
                                    showDownloadDialog = true
                                }
                            }
                            newWebView.loadUrl("about:blank")
                            tabWebViews.add(newWebView)
                        }
                        while (tabWebViews.size > tabs.size) {
                            val removed = tabWebViews.removeAt(tabWebViews.size - 1)
                            removed.destroy() // 显式释放
                        }
                    }

                    LaunchedEffect(currentTabIndex, tabWebViews.size, tabs) {
                        webViewRef = tabWebViews.getOrNull(currentTabIndex)
                        // 切换标签或重置时，如果新标签是空的，重置进度条
                        if (tabs.getOrNull(currentTabIndex) == null) {
                            webLoadingProgress = 0f
                        }

                        // 强力同步 Guard：定期检查 WebView 实际 URL 与 tabs 状态是否一致
                        // 解决 Back/Forward 导航后 tabs 状态更新不及时导致的 Home A/B 混乱问题
                        while (true) {
                            delay(500)
                            val webView = tabWebViews.getOrNull(currentTabIndex)
                            val actualUrl = webView?.url
                            if (actualUrl != null && tabs.getOrNull(currentTabIndex) != actualUrl) {
                                withContext(Dispatchers.Main) {
                                    tabs = tabs.toMutableList().also { it[currentTabIndex] = actualUrl }
                                }
                            }
                        }
                    }

                    val currentUrl = tabs.getOrNull(currentTabIndex)

                    val bottomInset = WindowInsets.navigationBars
                        .asPaddingValues().calculateBottomPadding()
                    val topInset = WindowInsets.systemBars
                        .asPaddingValues().calculateTopPadding()
                    val navBarVisualHeight = 56.dp
                    val headerVisualHeight = 60.dp // 52.dp content + some buffer

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .consumeWindowInsets(innerPadding)
                    ) {
                        // ── 内容区 ──
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            if (tabs.isNotEmpty()) {
                                // ── 背景层：始终填充全屏，且保持静止 ──
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer { alpha = if (isBackgroundReady) 1f else 0f }
                                ) {
                                    // 性能优化：仅在动画进行时暂停视频，动画结束后恢复播放
                                    // 检测覆盖层状态变化（打开或关闭），暂停视频释放 GPU 给动画。
                                    // 处于网页模式时也暂停视频，节省系统资源。
                                    val overlaySnapshot = listOf(showMenu, showHistory, showBookmarks, 
                                        showDownloads, showSettings, showCookiesSettings, 
                                        showWallpaperPicker, showTabOverview, showMusicPlayer, isSearchFocused)
                                    var isAnimating by remember { mutableStateOf(false) }
                                    LaunchedEffect(overlaySnapshot) {
                                        isAnimating = true
                                        delay(300) // 匹配动画时长，动画结束后恢复视频
                                        isAnimating = false
                                    }
                                    
                                    val isWebMode = currentUrl != null && currentUrl != "about:blank"
                                    val shouldPauseVideo = isAnimating || isWebMode
                                    
                                    if (wallpaperType == "video") {
                                        VideoBackground(
                                            modifier = Modifier, 
                                            path = wallpaperPath,
                                            exoPlayer = exoPlayer,
                                            isPaused = shouldPauseVideo,
                                            onReady = { isWallpaperLoaded = true }
                                        )
                                    } else {
                                        StaticBackground(
                                            modifier = Modifier, 
                                            path = wallpaperPath,
                                            onReady = { isWallpaperLoaded = true }
                                        )
                                    }
                                }
                            }

                            if ((currentUrl == null || currentUrl == "about:blank") && tabs.isNotEmpty()) {
                                // ── 搜索层：长按时缩放 ──
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer {
                                            alpha = if (isBackgroundReady) {
                                                lerp(
                                                    start = 1f,
                                                    stop = 0.5f,
                                                    fraction = (1f - homeScaleAnim.value) / 0.12f
                                                ).coerceIn(0f, 1f)
                                            } else 0f
                                            scaleX = homeScaleAnim.value
                                            scaleY = homeScaleAnim.value
                                        }
                                ) {
                                    SearchScreen(
                                        onSearch = { url ->
                                            tabs = tabs.toMutableList().also { it[currentTabIndex] = url }
                                        },
                                        onLongPress = { showWallpaperPicker = true },
                                        onSearchFocusChanged = { isSearchFocused = it },
                                        currentPlayingSong = currentPlayingSong,
                                        isMusicPlaying = isMusicPlaying,
                                        musicPositionProvider = { musicPosition },
                                        lyricLines = lyricLines,
                                        translationLines = translationLines,
                                        isWhiteIcon = isWhiteIcon
                                    )
                                }
                            } else if (currentUrl != null && currentUrl != "about:blank") {
                                val currentWebView = tabWebViews.getOrNull(currentTabIndex)
                                if (currentWebView != null) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.White)
                                            .padding(
                                                top = headerVisualHeight + topInset,
                                                bottom = navBarVisualHeight + bottomInset
                                            )
                                    ) {
                                        WebViewScreen(
                                            webView = currentWebView,
                                            url = currentUrl,
                                            onBack = {
                                                currentWebView.loadUrl("about:blank")
                                                tabs = tabs.toMutableList().also { it[currentTabIndex] = "about:blank" }
                                            }
                                        )
                                    }
                                }
                            } else {
                                // Empty state when 0 tabs
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("点击 + 开启新窗口", color = Color.Gray)
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = currentUrl != null && currentUrl != "about:blank",
                            enter = fadeIn(tween(400)),
                            exit = fadeOut(tween(300))
                        ) {
                            WebHeader(
                                url = currentUrl ?: "",
                                loadingProgress = webLoadingProgress,
                                isNightMode = isNightMode,
                                isBookmarked = bookmarksList.any { it.url == currentUrl },
                                onSearch = { newUrl ->
                                    tabs = tabs.toMutableList().also { it[currentTabIndex] = newUrl }
                                },
                                onAddBookmark = {
                                    val title = tabWebViews.getOrNull(currentTabIndex)?.title ?: currentUrl ?: "Untitled"
                                    if (bookmarksList.any { it.url == currentUrl }) {
                                        // 已收藏，取消收藏
                                        bookmarksList.removeAll { it.url == currentUrl }
                                        android.widget.Toast.makeText(context, "已取消收藏", android.widget.Toast.LENGTH_SHORT).show()
                                    } else {
                                        // 未收藏，弹出确认弹窗
                                        pendingBookmarkTitle = title
                                        pendingBookmarkUrl = currentUrl ?: ""
                                        showBookmarkDialog = true
                                    }
                                },
                                onRefresh = { webViewRef?.reload() }
                            )
                        }

                        // ── 窗口管理（缩略图） ──
                        if (showTabOverview) {
                            TabOverviewScreen(
                                tabs = tabs,
                                currentTabIndex = currentTabIndex,
                                tabsResetVersion = tabsResetVersion,
                                isNightMode = isNightMode,
                                onSelectTab = { index ->
                                    currentTabIndex = index
                                    showTabOverview = false
                                    webViewRef = null
                                },
                                onCloseTab = { index ->
                                    val mutable = tabs.toMutableList()
                                    mutable.removeAt(index)
                                    if (index < tabWebViews.size) {
                                        tabWebViews.removeAt(index)
                                    }
                                    if (mutable.isEmpty()) {
                                        // 清除所有残留的 WebView 引用，确保完全重置
                                        tabWebViews.clear()
                                        // 版本号自增 → key 变化 → Pager 强制重建
                                        tabsResetVersion++
                                        tabs = listOf("about:blank")
                                        currentTabIndex = 0
                                    } else {
                                        tabs = mutable
                                        currentTabIndex = currentTabIndex.coerceAtMost(tabs.size - 1)
                                    }
                                },
                                onNewTab = {
                                    tabs = tabs + listOf("about:blank")
                                    currentTabIndex = tabs.size - 1
                                    showTabOverview = false
                                    webViewRef = null
                                }
                            )
                        }

                        // ── 底部导航栏 ──
                        BottomNavBar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .graphicsLayer { alpha = if (isBackgroundReady) 1f else 0f },
                            navBarVisualHeight = navBarVisualHeight,
                            tabCount = tabs.size,
                            wallpaperType = wallpaperType,
                            wallpaperPath = wallpaperPath,
                            isWebMode = currentUrl != null && currentUrl != "about:blank", // 修正：判断是否处于网页模式
                            isNightMode = isNightMode,
                            onBack = {
                                if (currentUrl == "about:blank") {
                                    if (showWallpaperPicker) {
                                        showWallpaperPicker = false
                                    } else if (showMenu) {
                                        showMenu = false
                                    } else if (showTabOverview) {
                                        showTabOverview = false
                                    }
                                } else {
                                    val webView = webViewRef
                                    if (webView?.canGoBack() == true) {
                                        webView.goBack()
                                        // 立即触发一次同步
                                        val newUrl = webView.url
                                        if (newUrl != null && tabs.getOrNull(currentTabIndex) != newUrl) {
                                            tabs = tabs.toMutableList().also { it[currentTabIndex] = newUrl }
                                        }
                                    } else {
                                        val webView = webViewRef
                                        webView?.loadUrl("about:blank")
                                        tabs = tabs.toMutableList().also { it[currentTabIndex] = "about:blank" }
                                    }
                                }
                            },
                            onForward = { 
                                val webView = webViewRef
                                if (webView?.canGoForward() == true) {
                                    webView.goForward()
                                    // 立即触发一次同步
                                    val newUrl = webView.url
                                    if (newUrl != null && tabs.getOrNull(currentTabIndex) != newUrl) {
                                        tabs = tabs.toMutableList().also { it[currentTabIndex] = newUrl }
                                    }
                                }
                            },
                            onMenu = { 
                                showMenu = true 
                                focusManager.clearFocus() 
                            },
                            onTabOverview = { 
                                showTabOverview = true 
                                focusManager.clearFocus() 
                            },
                            onMusicClick = { showMusicPlayer = true },
                            isOverviewMode = showTabOverview
                        )

                        // 拦截物理返回键
                        BackHandler(enabled = (showWallpaperPicker || showMenu || showTabOverview || customView != null || showMusicPlayer)) {
                            if (customView != null) {
                                tabWebViews.getOrNull(currentTabIndex)?.webChromeClient?.onHideCustomView()
                            } else if (showMusicPlayer) {
                                showMusicPlayer = false
                            } else if (showWallpaperPicker) {
                                showWallpaperPicker = false
                            } else if (showMenu) {
                                showMenu = false
                            } else if (showTabOverview) {
                                showTabOverview = false
                            }
                        }

                        MusicOverlay(
                            visible = showMusicPlayer,
                            isNightMode = isNightMode,
                            onDismiss = { showMusicPlayer = false },
                            musicList = musicList,
                            currentPlayingSong = currentPlayingSong,
                            onCurrentPlayingSongChange = { currentPlayingSong = it },
                            isMusicPlaying = isMusicPlaying,
                            onIsMusicPlayingChange = { isMusicPlaying = it },
                            musicPosition = musicPosition,
                            onMusicPositionChange = { musicPosition = it },
                            musicDuration = musicDuration,
                            musicService = musicService,
                            lyricLines = lyricLines,
                            translationLines = translationLines,
                            client = client
                        )

                        MenuOverlay(
                            visible = showMenu,
                            isNightMode = isNightMode,
                            onDismiss = { showMenu = false },
                            onOpenHistory = { 
                                showMenu = false
                                showHistory = true 
                            },
                            onOpenBookmarks = {
                                showMenu = false
                                showBookmarks = true
                            },
                            onOpenDownloads = {
                                showMenu = false
                                showDownloads = true
                            },
                            onGoHome = {
                                showMenu = false
                                // 回到主页并清空历史，模拟新建标签页效果
                                val webView = tabWebViews.getOrNull(currentTabIndex)
                                if (webView != null) {
                                    webView.setTag(true)
                                    webView.loadUrl("about:blank")
                                }
                                tabs = tabs.toMutableList().also { it[currentTabIndex] = "about:blank" }
                            },
                            onOpenSettings = {
                                showMenu = false
                                showSettings = true
                            },
                            onToggleNightMode = {
                                isNightMode = !isNightMode
                                browserPrefs.edit().putBoolean("night_mode", isNightMode).apply()
                            },
                            onToggleDesktopSite = {
                                showMenu = false
                                isDesktopMode = !isDesktopMode
                                val webView = tabWebViews.getOrNull(currentTabIndex)
                                if (webView != null) {
                                    webView.settings.apply {
                                        userAgentString = if (isDesktopMode) {
                                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
                                        } else {
                                            android.webkit.WebSettings.getDefaultUserAgent(context)
                                                .replace("; wv", "").replace("Version/4.0 ", "")
                                        }
                                        setSupportZoom(isDesktopMode)
                                        builtInZoomControls = isDesktopMode
                                        displayZoomControls = false
                                    }
                                    webView.reload()
                                }
                                android.widget.Toast.makeText(
                                    context,
                                    if (isDesktopMode) "已切换为电脑版" else "已切换为手机版",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            },
                            isDesktopMode = isDesktopMode,
                            onExitBrowser = {
                                showMenu = false
                                (context as? ComponentActivity)?.finish()
                            }
                        )

                        // ── 历史记录页面 ──
                        AnimatedVisibility(
                            visible = showHistory,
                            enter = slideInHorizontally(animationSpec = tween(250, easing = FastOutSlowInEasing), initialOffsetX = { it }),
                            exit = slideOutHorizontally(animationSpec = tween(200, easing = FastOutSlowInEasing), targetOffsetX = { (it * 1.5).toInt() })
                        ) {
                            HistoryScreen(
                                historyList = historyList,
                                onSelectUrl = { url ->
                                    // 在当前标签页打开
                                    tabs = tabs.toMutableList().also { it[currentTabIndex] = url }
                                    showHistory = false
                                },
                                onDeleteItem = { item -> historyList.remove(item) },
                                onClearAll = { clearCookies ->
                                    historyList.clear()
                                    if (clearCookies) {
                                        CookieManager.getInstance().removeAllCookies(null)
                                        cookieDomains.clear()
                                        android.widget.Toast.makeText(context, "历史与 Cookies 已清空", android.widget.Toast.LENGTH_SHORT).show()
                                    } else {
                                        android.widget.Toast.makeText(context, "历史记录已清空", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onDismiss = { 
                                    showHistory = false 
                                }
                            )
                        }

                        // ── 书签页面 ──
                        AnimatedVisibility(
                            visible = showBookmarks,
                            enter = slideInHorizontally(animationSpec = tween(250, easing = FastOutSlowInEasing), initialOffsetX = { it }),
                            exit = slideOutHorizontally(animationSpec = tween(200, easing = FastOutSlowInEasing), targetOffsetX = { (it * 1.5).toInt() })
                        ) {
                            BookmarkScreen(
                                bookmarksList = bookmarksList,
                                onSelectUrl = { url ->
                                    tabs = tabs.toMutableList().also { it[currentTabIndex] = url }
                                    showBookmarks = false
                                },
                                onDeleteItem = { item -> bookmarksList.remove(item) },
                                onDismiss = { 
                                    showBookmarks = false 
                                }
                            )
                        }

                        // ── 下载页面 ──
                        AnimatedVisibility(
                            visible = showDownloads,
                            enter = slideInHorizontally(animationSpec = tween(250, easing = FastOutSlowInEasing), initialOffsetX = { it }),
                            exit = slideOutHorizontally(animationSpec = tween(200, easing = FastOutSlowInEasing), targetOffsetX = { (it * 1.5).toInt() })
                        ) {
                            DownloadScreen(
                                downloadsList = downloadsList,
                                downloadScope = downloadScope,
                                okHttpClient = okHttpClient,
                                onDismiss = { 
                                    showDownloads = false 
                                }
                            )
                        }

                        // ── 设置页面 ──
                        AnimatedVisibility(
                            visible = showSettings,
                            enter = slideInHorizontally(animationSpec = tween(250, easing = FastOutSlowInEasing), initialOffsetX = { it }),
                            exit = slideOutHorizontally(animationSpec = tween(200, easing = FastOutSlowInEasing), targetOffsetX = { (it * 1.5).toInt() })
                        ) {
                            SettingsScreen(
                                isNightMode = isNightMode,
                                fontSize = webFontSize,
                                onFontSizeChange = { newSize ->
                                    webFontSize = newSize
                                    browserPrefs.edit().putInt("font_size", newSize).apply()
                                    // 应用到所有 WebView
                                    tabWebViews.forEach { wv ->
                                        wv.settings.textZoom = newSize
                                    }
                                },
                                isDesktopMode = isDesktopMode,
                                onToggleDesktopMode = {
                                    isDesktopMode = !isDesktopMode
                                    val webView = tabWebViews.getOrNull(currentTabIndex)
                                    webView?.settings?.apply {
                                        if (isDesktopMode) {
                                            userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
                                        } else {
                                            userAgentString = android.webkit.WebSettings.getDefaultUserAgent(context)
                                                .replace("; wv", "").replace("Version/4.0 ", "")
                                        }
                                        setSupportZoom(isDesktopMode)
                                        builtInZoomControls = isDesktopMode
                                        displayZoomControls = false
                                    }
                                    webView?.reload()
                                },
                                onToggleNightMode = {
                                    isNightMode = !isNightMode
                                    browserPrefs.edit().putBoolean("night_mode", isNightMode).apply()
                                },
                                onOpenCookiesSettings = { showCookiesSettings = true },
                                onOpenDownloadSettings = { showDownloadSettings = true },
                                onClearCache = {
                                    tabWebViews.forEach { wv ->
                                        wv.clearCache(true)
                                    }
                                    android.webkit.WebStorage.getInstance().deleteAllData()
                                    android.webkit.CookieManager.getInstance().removeAllCookies(null)
                                    android.webkit.CookieManager.getInstance().flush()
                                    cookieDomains.clear()
                                },
                                onDismiss = {
                                    showSettings = false
                                }
                            )
                        }

                        if (showDownloadSettings) {
                            val musicPath = getPathFromDocumentTreeUri(context, getMusicDownloadUri(context), true)
                            val filePath = getPathFromDocumentTreeUri(context, getFileDownloadUri(context), false)
                            DownloadSettingsDialog(
                                currentMusicPath = musicPath,
                                currentFilePath = filePath,
                                onChooseMusicFolder = {
                                    musicFolderLauncher.launch(null)
                                },
                                onChooseFileFolder = {
                                    fileFolderLauncher.launch(null)
                                },
                                onDismiss = {
                                    showDownloadSettings = false
                                }
                            )
                        }

                        // ── Cookies 管理页面 ──
                        AnimatedVisibility(
                            visible = showCookiesSettings,
                            enter = slideInHorizontally(animationSpec = tween(250, easing = FastOutSlowInEasing), initialOffsetX = { it }),
                            exit = slideOutHorizontally(animationSpec = tween(200, easing = FastOutSlowInEasing), targetOffsetX = { (it * 1.5).toInt() })
                        ) {
                            CookiesManagementScreen(
                                allowCookies = allowCookies,
                                onToggleAllowCookies = { enabled ->
                                    allowCookies = enabled
                                    browserPrefs.edit().putBoolean("allow_cookies", enabled).apply()
                                    CookieManager.getInstance().setAcceptCookie(enabled)
                                },
                                cookieDomains = cookieDomains,
                                onDeleteDomain = { domain ->
                                    cookieDomains.remove(domain)
                                    // 尝试清除该域名的 Cookie
                                    val cm = CookieManager.getInstance()
                                    cm.setCookie(domain, "expires=Thu, 01 Jan 1970 00:00:00 GMT")
                                    cm.setCookie(".$domain", "expires=Thu, 01 Jan 1970 00:00:00 GMT")
                                    cm.flush()
                                },
                                onDismiss = { showCookiesSettings = false }
                            )
                        }

                        // ── 收藏确认弹窗 ──
                        if (showBookmarkDialog) {
                            androidx.compose.ui.window.Dialog(onDismissRequest = { showBookmarkDialog = false }) {
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "添加收藏",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        TextField(
                                            value = pendingBookmarkTitle,
                                            onValueChange = { pendingBookmarkTitle = it },
                                            label = { Text("网站名称") },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = pendingBookmarkUrl,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray,
                                            maxLines = 2
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            TextButton(onClick = { showBookmarkDialog = false }) {
                                                Text("取消")
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Button(onClick = {
                                                bookmarksList.add(0, BookmarkItem(pendingBookmarkTitle, pendingBookmarkUrl))
                                                android.widget.Toast.makeText(context, "已收藏", android.widget.Toast.LENGTH_SHORT).show()
                                                showBookmarkDialog = false
                                            }) {
                                                Text("收藏")
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // ── 下载确认弹窗 ──
                        if (showDownloadDialog) {
                            DownloadConfirmDialog(
                                fileName = pendingDownloadName,
                                fileSize = pendingDownloadSize,
                                onConfirm = { newName ->
                                    showDownloadDialog = false
                                    // 启动下载逻辑
                                    val downloadItem = DownloadItem(
                                        id = System.currentTimeMillis(),
                                        fileName = newName,
                                        url = pendingDownloadUrl,
                                        size = pendingDownloadSize,
                                        timestamp = System.currentTimeMillis()
                                    )
                                    downloadsList.add(0, downloadItem)
                                    startDownloadTask(
                                        context = context,
                                        item = downloadItem,
                                        userAgent = pendingDownloadUserAgent,
                                        referer = pendingDownloadReferer,
                                        downloadsList = downloadsList,
                                        scope = downloadScope,
                                        client = okHttpClient
                                    ) { newId ->
                                        val index = downloadsList.indexOfFirst { it.id == downloadItem.id }
                                        if (index != -1) {
                                            downloadsList[index] = downloadsList[index].copy(downloadId = newId)
                                        }
                                    }
                                },
                                onDismiss = { showDownloadDialog = false }
                            )
                        }

                        // ── 壁纸选择器 ──
                        if (showWallpaperPicker) {
                            WallpaperPickerScreen(
                                initialType = wallpaperType,
                                currentWallpaperPath = wallpaperPath,
                                downloadProgress = downloadProgress,
                                downloadedPaths = downloadedPaths,
                                externalScope = downloadScope, // 修复：使用持久 scope 防止退出后下载中断
                                onUploadCustom = { uri ->
                                    val activity = context as? androidx.activity.ComponentActivity
                                    activity?.lifecycleScope?.launch(Dispatchers.IO) {
                                        try {
                                            val isVideo = context.contentResolver.getType(uri)?.contains("video") == true
                                            val type = if (isVideo) "video" else "static"
                                            val ext = if (isVideo) "mp4" else "jpg"
                                            val fileName = "custom_${System.currentTimeMillis()}.$ext"
                                            val destFile = File(context.filesDir, "wallpapers/$type/$fileName").also { it.parentFile?.mkdirs() }
                                            
                                            context.contentResolver.openInputStream(uri)?.use { input ->
                                                destFile.outputStream().use { output ->
                                                    input.copyTo(output)
                                                }
                                            }
                                            
                                            withContext(Dispatchers.Main) {
                                                val path = destFile.absolutePath
                                                // 自动刷新已下载列表
                                                downloadedPaths["${type}_custom_${System.currentTimeMillis()}"] = path
                                                // 立即设为壁纸
                                                wallpaperType = type
                                                wallpaperPath = path
                                                wallpaperPrefs.edit()
                                                    .putString("type", type)
                                                    .putString("path", path)
                                                    .apply()
                                                showWallpaperPicker = false
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                },
                                onDismiss = { showWallpaperPicker = false },
                                onSelectWallpaper = { type, path ->
                                    wallpaperType = type
                                    wallpaperPath = path
                                    wallpaperPrefs.edit()
                                        .putString("type", type)
                                        .putString("path", path)
                                        .apply()
                                    showWallpaperPicker = false
                                }
                            )
                        }

                        // ── 全屏视频覆盖层 ──
                        if (customView != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                                    .pointerInput(Unit) {
                                        detectTapGestures { /* 阻止点击穿透 */ }
                                    }
                            ) {
                                AndroidView(
                                    factory = { customView!! },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        // ── 启动开屏页 (kaiping.png) ──
                        // 使用 R.drawable.kaiping 同步加载，与系统闪屏无缝衔接，避免黑屏
                        AnimatedVisibility(
                            visible = !isBackgroundReady,
                            enter = EnterTransition.None,
                            exit = fadeOut(tween(300))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.kaiping),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}@Composable
fun DownloadConfirmDialog(
    fileName: String,
    fileSize: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(fileName) }
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "下载确认",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("文件名") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "预计大小: $fileSize",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("取消")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onConfirm(name) }) {
                        Text("下载")
                    }
                }
            }
        }
    }
}

// ════════════════════════════════════════
//  底部导航栏
// ════════════════════════════════════════
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navBarVisualHeight: androidx.compose.ui.unit.Dp,
    tabCount: Int,
    wallpaperType: String,
    wallpaperPath: String,
    isWebMode: Boolean,
    onBack: () -> Unit,
    onForward: () -> Unit,
    onMenu: () -> Unit,
    onTabOverview: () -> Unit,
    onMusicClick: () -> Unit,
    isOverviewMode: Boolean = false,
    isNightMode: Boolean = false
) {
    // 第2、5张静态 (static_2.jpg, static_5.jpg), 第1张动态 (video_1.mp4), 第4张动态 (video_4.mp4)
    val isWhiteIcon = remember(wallpaperType, wallpaperPath, isWebMode, isOverviewMode, isNightMode) {
        if (isNightMode && isWebMode) return@remember true // 夜间模式+网页模式下使用白色图标
        if (isWebMode || isOverviewMode) return@remember false // 网页模式或预览模式下强制不使用白色图标
        (wallpaperType == "static" && (
                wallpaperPath.endsWith("static_2.jpg") || 
                wallpaperPath.endsWith("static_5.jpg") ||
                wallpaperPath.endsWith("static_7.jpg") ||
                wallpaperPath.endsWith("static_9.jpg") ||
                wallpaperPath.endsWith("static_11.jpg") ||
                wallpaperPath.endsWith("static_12.jpg")
        )) ||
        (wallpaperType == "video" && (
                wallpaperPath.endsWith("video_1.mp4") || 
                wallpaperPath.endsWith("video_4.mp4") ||
                wallpaperPath.endsWith("video_8.mp4")
        ))
    }
    val iconTint = if (isWhiteIcon) Color.White else Color.Black

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // 背景层：主页始终透明，仅在网页模式+夜间模式下为深色
        Surface(
            modifier = Modifier.matchParentSize(),
            color = if (isNightMode && isWebMode) Color(0xFF1A1A1A) else Color.Transparent
        ) {}

        // 按钮层
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(navBarVisualHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "后退", tint = iconTint)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onForward),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, "前进", tint = iconTint)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onMenu),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Menu, "菜单", tint = iconTint)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onTabOverview),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(2.dp, iconTint, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tabCount.toString(),
                            color = iconTint,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onMusicClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.MusicNote, "音乐播放器", tint = iconTint)
                }
            }
        }
    }
}@Composable
fun TabOverviewScreen(
    tabs: List<String?>,
    currentTabIndex: Int,
    tabsResetVersion: Int,
    isNightMode: Boolean = false,
    onSelectTab: (Int) -> Unit,
    onCloseTab: (Int) -> Unit,
    onNewTab: () -> Unit
) {
    val scope = rememberCoroutineScope()
    // 颜色适配
    val bgColor = if (isNightMode) Color(0xFF121212) else Color(0xFFF2F2F7)
    val cardColor = if (isNightMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isNightMode) Color.White else Color.Black
    val subTextColor = if (isNightMode) Color.LightGray else Color.Gray

    // key 加入 tabsResetVersion，确保删除最后一个 tab 重置后 Pager 强制重建
    key(tabs, tabsResetVersion) {
        val pagerState = rememberPagerState(
            initialPage = if (currentTabIndex < tabs.size) currentTabIndex else 0,
            pageCount = { tabs.size }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .systemBarsPadding()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${tabs.size} 个窗口",
                        style = MaterialTheme.typography.titleLarge,
                        color = textColor
                    )
                    
                    IconButton(onClick = onNewTab) {
                        Icon(Icons.Default.Add, "新建", tint = if (isNightMode) Color(0xFF64B5F6) else Color.Blue, modifier = Modifier.size(32.dp))
                    }
                }

                if (tabs.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(64.dp).clickable { onNewTab() }, tint = subTextColor)
                            Spacer(Modifier.height(8.dp))
                            Text("点击 + 开启新窗口", color = subTextColor)
                        }
                    }
                } else {
                    // key 加入 tabsResetVersion，确保删除最后一个 tab 重置后 Pager 强制重建
                    key(tabs, tabsResetVersion) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(bottom = 120.dp), // 再次提高位置
                            contentAlignment = Alignment.Center
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                contentPadding = PaddingValues(horizontal = 60.dp),
                                pageSpacing = 16.dp,
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) { page ->
                                val url = if (page < tabs.size) tabs[page] else null
                                // 使用 key(page) 确保每个页面的状态 independent
                                val offsetY = remember { Animatable(0f) }
                                val scaleAnim = remember { Animatable(0.7f) }

                                LaunchedEffect(Unit) {
                                    scaleAnim.animateTo(1f, tween(450, easing = FastOutSlowInEasing))
                                }
                                
                                val draggableState = rememberDraggableState { delta ->
                                    if (offsetY.value + delta <= 0) {
                                        scope.launch {
                                            offsetY.snapTo(offsetY.value + delta)
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .graphicsLayer {
                                            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                                            val focusScale = lerp(
                                                start = 0.85f,
                                                stop = 1f,
                                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                            )
                                            scaleX = focusScale * scaleAnim.value
                                            scaleY = focusScale * scaleAnim.value
                                            alpha = lerp(
                                                start = 0.6f,
                                                stop = 1f,
                                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                            )
                                        }
                                        .fillMaxWidth()
                                        .height(420.dp)
                                        .offset { IntOffset(0, offsetY.value.toInt()) }
                                        .draggable(
                                            state = draggableState,
                                            orientation = Orientation.Vertical,
                                            onDragStopped = { velocity ->
                                                if (offsetY.value < -300f || velocity < -1000f) {
                                                    scope.launch {
                                                        offsetY.animateTo(
                                                            targetValue = -2000f,
                                                            animationSpec = tween(500)
                                                        )
                                                        onCloseTab(page)
                                                    }
                                                } else {
                                                    scope.launch {
                                                        offsetY.animateTo(0f, tween(350))
                                                    }
                                                }
                                            }
                                        )
                                        .clickable { onSelectTab(page) }
                                ) {
                                    Card(
                                        modifier = Modifier.fillMaxSize(),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = cardColor),
                                        elevation = CardDefaults.cardElevation(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier.padding(24.dp)
                                            ) {
                                                val isHome = url == null || url == "about:blank"
                                                Text(
                                                    text = if (isHome) "主页" else {
                                                        val display = url.replace("https://", "").replace("http://", "")
                                                        if (display.length > 25) display.take(25) + "..." else display
                                                    },
                                                    style = MaterialTheme.typography.titleMedium,
                                                    textAlign = TextAlign.Center,
                                                    color = textColor,
                                                    maxLines = 1
                                                )
                                                Spacer(modifier = Modifier.height(20.dp))
                                                if (isHome) {
                                                    Icon(Icons.Default.Home, null, modifier = Modifier.size(80.dp), tint = subTextColor)
                                                } else {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(200.dp)
                                                            .background(if (isNightMode) Color(0xFF2C2C2C) else Color(0xFFF0F0F0), RoundedCornerShape(8.dp)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = url!!,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = subTextColor,
                                                            textAlign = TextAlign.Center,
                                                            modifier = Modifier.padding(16.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}// ════════════════════════════════════════
//  菜单蒙层（从下到上滑入白板）
// ════════════════════════════════════════
@Composable
fun MenuOverlay(
    visible: Boolean,
    isNightMode: Boolean = false,
    onDismiss: () -> Unit,
    onOpenHistory: () -> Unit = {},
    onOpenBookmarks: () -> Unit = {},
    onOpenDownloads: () -> Unit = {},
    onGoHome: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
    onToggleNightMode: () -> Unit = {},
    onToggleDesktopSite: () -> Unit = {},
    isDesktopMode: Boolean = false,
    onExitBrowser: () -> Unit = {}
) {
    val density = LocalDensity.current
    val sheetHeightPx = remember(density) { with(density) { 260.dp.toPx() } }

    var shouldRender by remember { mutableStateOf(visible) }
    if (visible) {
        shouldRender = true
    }
    val offsetY   = remember { Animatable(sheetHeightPx) }
    val dimAlpha  = remember { Animatable(0f) }

    LaunchedEffect(visible) {
        if (visible) {
            shouldRender = true
            launch { offsetY.animateTo(0f, tween(300, easing = FastOutSlowInEasing)) }
            dimAlpha.animateTo(0.55f, tween(300))
        } else {
            launch { offsetY.animateTo(sheetHeightPx, tween(250, easing = FastOutSlowInEasing)) }
            dimAlpha.animateTo(0f, tween(250))
            delay(250)
            shouldRender = false
        }
    }

    if (!shouldRender) return

    val panelBg = if (isNightMode) Color(0xFF1E1E1E) else Color.White
    val titleColor = if (isNightMode) Color.White else Color.Black
    val iconColor = if (isNightMode) Color.LightGray else Color.DarkGray
    val labelColor = if (isNightMode) Color.Gray else Color.Gray

    Box(modifier = Modifier.fillMaxSize()) {
        // 性能优化：使用 drawBehind 避免动画期间触发重组
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 1f } // 创建独立绘制层
                .drawBehind {
                    drawRect(Color.Black.copy(alpha = dimAlpha.value))
                }
                .then(
                    if (visible) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onDismiss
                        )
                    } else {
                        Modifier
                    }
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(260.dp)
                .graphicsLayer { translationY = offsetY.value }
                .background(panelBg, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .navigationBarsPadding()
                .then(
                    if (visible) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {}
                    } else {
                        Modifier
                    }
                )
        ) {
            Spacer(Modifier.height(18.dp))

            Text(
                text = "菜单",
                style = MaterialTheme.typography.titleMedium,
                color = titleColor,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(16.dp))

            val menuItems = listOf(
                Triple(Icons.Default.Bookmarks, "书签", onOpenBookmarks),
                Triple(Icons.Default.History, "历史", onOpenHistory),
                Triple(Icons.Default.Download, "下载", onOpenDownloads),
                Triple(Icons.Default.Home, "主页", onGoHome),
                Triple(Icons.Default.Settings, "设置", onOpenSettings),
                Triple(
                    if (isNightMode) Icons.Default.LightMode else Icons.Default.NightsStay,
                    if (isNightMode) "白天" else "夜间",
                    onToggleNightMode
                ),
                Triple(
                    if (isDesktopMode) Icons.Default.PhoneAndroid else Icons.Default.Computer,
                    if (isDesktopMode) "手机版" else "电脑版",
                    onToggleDesktopSite
                ),
                Triple(Icons.AutoMirrored.Filled.ExitToApp, "退出", onExitBrowser)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(menuItems) { (icon, label, onClick) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onClick() }
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.height(4.dp))
                        Text(label, fontSize = 12.sp, color = labelColor)
                    }
                }
            }
        }
    }
}// ════════════════════════════════════════
//  网页顶部搜索栏
// ════════════════════════════════════════
@Composable
fun WebHeader(
    url: String,
    loadingProgress: Float,
    isNightMode: Boolean = false,
    isBookmarked: Boolean = false,
    onSearch: (String) -> Unit,
    onAddBookmark: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var text by remember(url) { mutableStateOf(url) }
    val focusManager = LocalFocusManager.current

    val headerBg = if (isNightMode) Color(0xFF1A1A1A) else Color.White
    val fieldBg = if (isNightMode) Color(0xFF2C2C2C) else Color(0xFFF1F3F4)
    val textColor = if (isNightMode) Color.White else Color.Black
    val hintColor = if (isNightMode) Color.Gray else Color.Gray

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerBg),
        color = headerBg,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 0.dp, bottom = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "刷新",
                        tint = textColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(4.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(fieldBg, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = hintColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                            if (text.isEmpty()) {
                                Text("Search or enter URL", fontSize = 14.sp, color = hintColor)
                            }
                            BasicTextField(
                                value = text,
                                onValueChange = { text = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                textStyle = TextStyle(color = textColor, fontSize = 14.sp),
                                cursorBrush = SolidColor(textColor),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        if (text.isNotBlank()) {
                                            val finalUrl = if (text.startsWith("http") || text.startsWith("www.") || (text.contains(".") && !text.contains(" "))) {
                                                if (text.startsWith("http")) text else "https://$text"
                                            } else {
                                                searchEngines[0].searchUrl + Uri.encode(text)
                                            }
                                            onSearch(finalUrl)
                                            focusManager.clearFocus()
                                        }
                                    }
                                )
                            )
                        }
                        if (text.isNotEmpty()) {
                            IconButton(
                                onClick = { text = "" },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = hintColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                if (text != url) {
                    Text(
                        text = "Go",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 2.dp)
                            .clickable {
                                if (text.isNotBlank()) {
                                    val finalUrl = if (text.startsWith("http") || text.startsWith("www.") || (text.contains(".") && !text.contains(" "))) {
                                        if (text.startsWith("http")) text else "https://$text"
                                    } else {
                                        searchEngines[0].searchUrl + Uri.encode(text)
                                    }
                                    onSearch(finalUrl)
                                    focusManager.clearFocus()
                                }
                            }
                    )
                }

                // 收藏按钮：已收藏显示实心星，未收藏显示带加号的空心星
                IconButton(
                    onClick = onAddBookmark,
                    modifier = Modifier.padding(start = 2.dp)
                ) {
                    if (isBookmarked) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "已收藏",
                            tint = Color(0xFFFFA726),
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.StarBorder,
                                contentDescription = "收藏",
                                tint = if (isNightMode) Color.LightGray else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = if (isNightMode) Color.LightGray else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                    }
                }
            }
            
            // 进度条
            if (loadingProgress > 0f && loadingProgress < 1f) {
                LinearProgressIndicator(
                    progress = { loadingProgress },
                    modifier = Modifier.fillMaxWidth().height(2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Transparent
                )
            } else {
                Spacer(Modifier.height(2.dp))
            }
        }
    }
}// ════════════════════════════════════════
//  WebView（将引用回调给上层）
// ════════════════════════════════════════
@Composable
fun WebViewScreen(webView: WebView, url: String, onBack: () -> Unit) {
    DisposableEffect(webView) {
        webView.onResume()
        onDispose {
            webView.onPause()
        }
    }
    LaunchedEffect(url) {
        if (webView.url != url) {
            webView.scrollTo(0, 0)
            webView.loadUrl(url)
        }
    }

    BackHandler {
        if (webView.canGoBack()) webView.goBack()
        else onBack()
    }

    AndroidView(factory = { webView }, modifier = Modifier.fillMaxSize())
}

// ════════════════════════════════════════
//  视频背景
// ════════════════════════════════════════
@OptIn(UnstableApi::class)
@Composable
fun VideoBackground(
    modifier: Modifier = Modifier,
    path: String = "asset:///background.mp4",
    exoPlayer: ExoPlayer,
    isPaused: Boolean = false,
    onReady: (() -> Unit)? = null
) {
    val alphaAnim = remember { Animatable(0f) }
    var isVideoVisible by remember { mutableStateOf(false) }

    // 监听视频渲染状态
    val listener = remember(onReady) {
        object : Player.Listener {
            override fun onRenderedFirstFrame() {
                isVideoVisible = true
                onReady?.invoke()
            }
        }
    }

    DisposableEffect(exoPlayer) {
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    // 当路径变化时，更新媒体项并重新 prepare
    LaunchedEffect(path) {
        val uri = if (path.startsWith("asset:///")) Uri.parse(path)
                  else Uri.fromFile(File(path))
        exoPlayer.setMediaItem(MediaItem.fromUri(uri))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = !isPaused
    }

    // 性能优化：覆盖层打开时暂停视频解码，释放 GPU 资源给 UI 动画
    // 用户看不到差异（视频被覆盖层遮挡），但动画会明显更流畅
    LaunchedEffect(isPaused) {
        exoPlayer.playWhenReady = !isPaused
    }

    // 当 VideoBackground 重新进入组合时，确保播放器恢复播放
    DisposableEffect(Unit) {
        if (exoPlayer.playbackState == Player.STATE_IDLE) {
            exoPlayer.prepare()
        }
        if (!isPaused) exoPlayer.playWhenReady = true
        onDispose {}
    }

    // 生命周期感知：App 进入后台时暂停，前台时恢复
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            when (event) {
                androidx.lifecycle.Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.playWhenReady = false
                }
                androidx.lifecycle.Lifecycle.Event.ON_RESUME -> {
                    // 只有在没有覆盖层时才恢复播放
                    if (!isPaused) {
                        exoPlayer.playWhenReady = true
                        if (exoPlayer.playbackState == Player.STATE_IDLE ||
                            exoPlayer.playbackState == Player.STATE_ENDED) {
                            exoPlayer.prepare()
                        }
                    }
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(isVideoVisible) {
        if (isVideoVisible) {
            alphaAnim.animateTo(1f, tween(400))
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                keepScreenOn = true
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                // 性能优化：使用 SurfaceView 代替默认的 TextureView
                // SurfaceView 有独立的绘制表面，不与 Compose UI 共享 GPU 管线，减少争抢
                setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = { 
            if (it.player != exoPlayer) it.player = exoPlayer
            it.alpha = alphaAnim.value
        },
        modifier = Modifier.fillMaxSize().then(modifier)
    )
}

// ════════════════════════════════════════
//  静态背景
// ════════════════════════════════════════
@Composable
fun StaticBackground(
    modifier: Modifier = Modifier,
    path: String,
    onReady: (() -> Unit)? = null
) {
    val context = LocalContext.current
    // 性能优化：在后台线程解码大图，按屏幕分辨率缩放避免内存和渲染压力
    val bitmap by androidx.compose.runtime.produceState<androidx.compose.ui.graphics.ImageBitmap?>(initialValue = null, path) {
        value = withContext(Dispatchers.IO) {
            try {
                val screenWidth = context.resources.displayMetrics.widthPixels
                val screenHeight = context.resources.displayMetrics.heightPixels

                // 第一步：只解码边界信息
                val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                if (path.startsWith("asset:///")) {
                    context.assets.open(path.removePrefix("asset:///")).use { BitmapFactory.decodeStream(it, null, opts) }
                } else {
                    BitmapFactory.decodeFile(path, opts)
                }

                // 第二步：计算采样率
                val inSampleSize = calculateInSampleSize(opts.outWidth, opts.outHeight, screenWidth, screenHeight)
                val decodeOpts = BitmapFactory.Options().apply {
                    this.inSampleSize = inSampleSize
                    inPreferredConfig = android.graphics.Bitmap.Config.RGB_565  // 省一半内存
                }

                // 第三步：解码
                val bmp = if (path.startsWith("asset:///")) {
                    context.assets.open(path.removePrefix("asset:///")).use { BitmapFactory.decodeStream(it, null, decodeOpts) }
                } else {
                    BitmapFactory.decodeFile(path, decodeOpts)
                }
                bmp?.asImageBitmap()
            } catch (e: Exception) { null }
        }
        // 当 bitmap 非空时回调 onReady
        if (value != null) {
            onReady?.invoke()
        }
    }
    
    if (bitmap != null) {
        Image(
            bitmap = bitmap!!,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().then(modifier)
        )
    }
}/** 计算 BitmapFactory 采样率，保证解码后的尺寸刚好覆盖目标区域 */
private fun calculateInSampleSize(rawW: Int, rawH: Int, targetW: Int, targetH: Int): Int {
    var inSampleSize = 1
    if (rawW > targetW || rawH > targetH) {
        val halfW = rawW / 2
        val halfH = rawH / 2
        while (halfW / inSampleSize >= targetW && halfH / inSampleSize >= targetH) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

// ════════════════════════════════════════
//  主页歌词组件（仅限在此组件内触发重组，隔离主页/搜索框重组）
// ════════════════════════════════════════
@Composable
fun HomeScreenLyrics(
    isMusicPlaying: Boolean,
    currentPlayingSong: MusicItem?,
    musicPositionProvider: () -> Long,
    lyricLines: List<LyricLine>,
    translationLines: List<LyricLine>,
    isFocused: Boolean,
    isTransitioning: Boolean
) {
    if (isMusicPlaying && currentPlayingSong != null && lyricLines.isNotEmpty() && !isFocused && !isTransitioning) {
        val musicPosition = musicPositionProvider()
        val currentLyricIndex = remember(lyricLines.size, musicPosition) {
            lyricLines.indexOfLast { it.timeMs <= musicPosition + 300 }
        }
        val currentTranslationIndex = remember(translationLines.size, musicPosition) {
            translationLines.indexOfLast { it.timeMs <= musicPosition + 300 }
        }
        val targetLine = if (currentLyricIndex != -1) lyricLines.getOrNull(currentLyricIndex) else null
        val targetTrans = if (currentTranslationIndex != -1) translationLines.getOrNull(currentTranslationIndex)?.text else null

        val shadowStyle = TextStyle(
            shadow = androidx.compose.ui.graphics.Shadow(
                color = Color.Black.copy(alpha = 0.8f),
                offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                blurRadius = 6f
            )
        )

        if (targetLine != null || !targetTrans.isNullOrBlank()) {
            val offsetDp = if (targetTrans.isNullOrBlank()) (-45).dp else (-65).dp
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .offset(y = offsetDp)
            ) {
                if (targetLine != null) {
                    Text(
                        text = targetLine.text,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = shadowStyle
                    )
                }
                if (!targetTrans.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = targetTrans,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        style = shadowStyle
                    )
                }
            }
        }
    }
}

// ════════════════════════════════════════
//  搜索页
// ════════════════════════════════════════
@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    onLongPress: () -> Unit = {},
    onSearchFocusChanged: (Boolean) -> Unit = {},
    client: OkHttpClient? = null,
    currentPlayingSong: MusicItem? = null,
    isMusicPlaying: Boolean = false,
    musicPositionProvider: () -> Long = { 0L },
    lyricLines: List<LyricLine> = emptyList(),
    translationLines: List<LyricLine> = emptyList(),
    isWhiteIcon: Boolean = false
) {
    val context = LocalContext.current
    // 性能优化：复用 MainActivity 的 OkHttpClient 而不是每次创建新实例
    val effectiveClient = client ?: remember { (context as? MainActivity)?.client ?: OkHttpClient() }
    val sharedPrefs = remember { context.getSharedPreferences("browser_prefs", Context.MODE_PRIVATE) }
    
    var query by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isFocused by remember { mutableStateOf(false) }
    var currentEngineIndex by remember { 
        mutableStateOf(sharedPrefs.getInt("last_engine_index", 0)) 
    }
    var isTransitioning by remember { mutableStateOf(false) }
    val selectedEngine = searchEngines[currentEngineIndex]

    val focusManager = LocalFocusManager.current
    val gson = remember { Gson() }
    val scope = rememberCoroutineScope()

    // --- Weather Logic ---
    var weatherInfo by remember { mutableStateOf<WeatherInfo?>(null) }
    var showGreeting by remember { mutableStateOf(true) }
    val greetingText = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..10 -> "早上好"
            in 11..13 -> "中午好"
            in 14..18 -> "下午好"
            else -> "晚上好"
        }
    }

    //  greeting 持续逻辑：至少显示 1.5 秒，但直到天气加载成功才切换
    LaunchedEffect(weatherInfo) {
        if (weatherInfo != null) {
            delay(1500) // 已经获取到天气，显示 1.5 秒问候语后切换
            showGreeting = false
        } else {
            // 如果 10 秒都没拿到天气，也强制切换（ fallback 应该会处理，这里是保底）
            delay(10000)
            showGreeting = false
        }
    }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val apiKey = "SR_Mc21H1zOS8CaF0"

    fun fetchWeather(location: String) {
        scope.launch(Dispatchers.IO) {
            var success = false
            repeat(2) { attempt -> // 尝试两次
                if (success) return@repeat
                try {
                    android.util.Log.d("Weather", "Fetching weather (attempt ${attempt + 1}) for: $location")
                    val url = "https://api.seniverse.com/v3/weather/now.json?key=$apiKey&location=$location&language=zh-Hans&unit=c"
                    val request = Request.Builder().url(url).build()
                    effectiveClient.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            android.util.Log.e("Weather", "API failed: ${response.code}")
                            return@use
                        }
                        val body = response.body.string()
                        val json = gson.fromJson(body, JsonObject::class.java)
                        val results = json.getAsJsonArray("results")?.get(0)?.asJsonObject
                        if (results != null) {
                            val locationObj = results.getAsJsonObject("location")
                            val nowObj = results.getAsJsonObject("now")
                            if (locationObj != null && nowObj != null) {
                                withContext(Dispatchers.Main) {
                                    weatherInfo = WeatherInfo(
                                        city = locationObj.get("name").asString,
                                        temp = nowObj.get("temperature").asString,
                                        text = nowObj.get("text").asString
                                    )
                                }
                                success = true
                                android.util.Log.d("Weather", "Success: ${weatherInfo?.city} ${weatherInfo?.text}")
                            }
                        }
                    }
                } catch (e: Exception) {
                    android.util.Log.e("Weather", "Error on attempt ${attempt + 1}", e)
                    delay(1000) // 失败重试前等待
                }
            }
            
            // 如果最终还是失败且不是北京，则强制 fallback 到北京
            if (!success && location != "beijing") {
                android.util.Log.d("Weather", "Final fallback to Beijing")
                fetchWeather("beijing")
            } else if (!success) {
                // 如果连北京的天气都加载失败，直接给一个保底数据，防止空白
                android.util.Log.d("Weather", "Everything failed, using safety default")
                withContext(Dispatchers.Main) {
                    weatherInfo = WeatherInfo("北京", "22", "晴")
                }
            }
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        
        if (fineGranted || coarseGranted) {
            // Get location
            try {
                fusedLocationClient.getCurrentLocation(
                    if (fineGranted) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    null
                ).addOnSuccessListener { location ->
                    if (location != null) {
                        fetchWeather("${location.latitude}:${location.longitude}")
                    } else {
                        fetchWeather("beijing")
                    }
                }.addOnFailureListener { fetchWeather("beijing") }
            } catch (e: SecurityException) { fetchWeather("beijing") }
        } else {
            fetchWeather("beijing")
        }
    }

    LaunchedEffect(Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        
        if (fineGranted || coarseGranted) {
            try {
                android.util.Log.d("Weather", "Location permission granted, fetching current location...")
                fusedLocationClient.getCurrentLocation(
                    if (fineGranted) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    null
                ).addOnSuccessListener { location ->
                    if (location != null) {
                        fetchWeather("${location.latitude}:${location.longitude}")
                    } else {
                        android.util.Log.d("Weather", "Current location is null, trying lastLocation...")
                        fusedLocationClient.lastLocation.addOnSuccessListener { lastLoc ->
                            if (lastLoc != null) {
                                fetchWeather("${lastLoc.latitude}:${lastLoc.longitude}")
                            } else {
                                fetchWeather("beijing")
                            }
                        }.addOnFailureListener { fetchWeather("beijing") }
                    }
                }.addOnFailureListener { 
                    android.util.Log.e("Weather", "Failed to get current location", it)
                    fetchWeather("beijing") 
                }
            } catch (e: SecurityException) { 
                android.util.Log.e("Weather", "SecurityException getting location", e)
                fetchWeather("beijing") 
            }
        } else {
            android.util.Log.d("Weather", "Location permission NOT granted, requesting...")
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }
    // --- End Weather Logic ---

    // 性能优化：在后台线程解码 Logo
    val loadBitmap by androidx.compose.runtime.produceState<androidx.compose.ui.graphics.ImageBitmap?>(initialValue = null, selectedEngine) {
        value = withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(selectedEngine.logoAsset)
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
            } catch (e: Exception) { null }
        }
    }

    val density = LocalDensity.current
    val unfocusedPx = remember(density) { with(density) { 500.dp.toPx() } }
    val focusedPx   = remember(density) { with(density) { 100.dp.toPx() } }
    val searchingPx = 0f

    val topOffsetAnim = remember { Animatable(unfocusedPx) }

    LaunchedEffect(isFocused, isTransitioning) {
        val target = when {
            isTransitioning -> searchingPx
            isFocused -> focusedPx
            else -> unfocusedPx
        }
        // 缩短动画时间提升响应感，减少卡顿感
        topOffsetAnim.animateTo(
            targetValue = target,
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
        )
    }

    // 性能优化：使用 Animatable + drawBehind 代替 animateColorAsState
    // animateColorAsState 每帧触发重组，drawBehind 只在绘制层更新，不触发重组
    val bgAlphaAnim = remember { Animatable(0f) }
    LaunchedEffect(isFocused, isTransitioning) {
        val targetAlpha = when {
            isTransitioning -> 1f
            isFocused -> 0.9f
            else -> 0f
        }
        bgAlphaAnim.animateTo(targetAlpha, tween(durationMillis = 350, easing = FastOutSlowInEasing))
    }

    LaunchedEffect(query, currentEngineIndex) {
        if (query.isNotBlank()) {
            delay(150) // 缩短防抖延迟，从 300ms 降至 150ms，显著提升输入响应感
            withContext(Dispatchers.IO) {
                try {
                    val request = Request.Builder()
                        .url("${selectedEngine.suggestUrl}${Uri.encode(query)}")
                        .build()
                    val response = effectiveClient.newCall(request).execute()
                    val body = response.body.string()
                    val type = object : TypeToken<List<Any>>() {}.type
                    val list: List<Any> = gson.fromJson(body, type)
                    if (list.size > 1 && list[1] is List<*>) {
                        suggestions = (list[1] as List<*>).filterIsInstance<String>().take(5)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            suggestions = emptyList()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer { alpha = 1f } // 创建独立绘制层
            .drawBehind {
                if (bgAlphaAnim.value > 0f) {
                    drawRect(Color.White.copy(alpha = bgAlphaAnim.value))
                }
            }
            .pointerInput(isFocused, isTransitioning) {
                coroutineScope {
                    awaitPointerEventScope {
                        while (true) {
                            val down = awaitFirstDown(requireUnconsumed = true)
                            val startPoint = down.position
                            val startTime = System.currentTimeMillis()
                            var movedTooFar = false
                            var isLongPressTriggered = false
                            val thresholdPx = 20.dp.toPx()
                            
                            val longPressJob = launch {
                                delay(viewConfiguration.longPressTimeoutMillis)
                                if (!movedTooFar && !isFocused && !isTransitioning) {
                                    isLongPressTriggered = true
                                    onLongPress()
                                }
                            }
                            
                            while (true) {
                                val event = awaitPointerEvent()
                                val change = event.changes.firstOrNull { it.id == down.id }
                                if (change == null || !change.pressed) {
                                    longPressJob.cancel()
                                    val duration = System.currentTimeMillis() - startTime
                                    val isConsumed = change?.isConsumed ?: false
                                    if (!movedTooFar && !isConsumed && duration < viewConfiguration.longPressTimeoutMillis && !isLongPressTriggered) {
                                        focusManager.clearFocus()
                                    }
                                    break
                                }
                                
                                if (change.isConsumed) {
                                    longPressJob.cancel()
                                    break
                                }
                                
                                val currentPos = change.position
                                val dx = currentPos.x - startPoint.x
                                val dy = currentPos.y - startPoint.y
                                if (dx * dx + dy * dy > thresholdPx * thresholdPx) {
                                    movedTooFar = true
                                    longPressJob.cancel()
                                }
                            }
                        }
                    }
                }
            }
            .systemBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
    // --- Weather & Greeting Widget ---
    AnimatedContent(
        targetState = showGreeting || weatherInfo == null,
        transitionSpec = {
            fadeIn(tween(1000)) togetherWith fadeOut(tween(1000))
        },
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp),
        label = "GreetingWeatherTransition"
    ) { isShowingGreeting ->
        if (!isFocused && !isTransitioning) {
            if (isShowingGreeting) {
                val greetingEmoji = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                    in 5..10 -> "🌅"
                    in 11..13 -> "☀️"
                    in 14..18 -> "🥰"
                    else -> "🌙"
                }
                Text(
                    text = "$greetingEmoji $greetingText",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
            } else if (weatherInfo != null) {
                val weatherEmoji = when {
                    weatherInfo?.text?.contains("晴") == true -> "☀️"
                    weatherInfo?.text?.contains("云") == true || weatherInfo?.text?.contains("阴") == true -> "☁️"
                    weatherInfo?.text?.contains("雨") == true -> "🌧️"
                    weatherInfo?.text?.contains("雪") == true -> "❄️"
                    weatherInfo?.text?.contains("雷") == true -> "⚡"
                    weatherInfo?.text?.contains("雾") == true || weatherInfo?.text?.contains("霾") == true -> "🌫️"
                    weatherInfo?.text?.contains("扬沙") == true || weatherInfo?.text?.contains("尘") == true -> "🌪️"
                    else -> "🌡️"
                }
                Text(
                    text = "$weatherEmoji ${weatherInfo?.city} ${weatherInfo?.text} ${weatherInfo?.temp}°C",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
            }
        }
    }
    // --- End Weather & Greeting Widget ---

        Box(
            modifier = Modifier
                .graphicsLayer { translationY = topOffsetAnim.value }
                .fillMaxWidth(if (isTransitioning) 1f else 0.95f),
            contentAlignment = Alignment.TopCenter
        ) {
            HomeScreenLyrics(
                isMusicPlaying = isMusicPlaying,
                currentPlayingSong = currentPlayingSong,
                musicPositionProvider = musicPositionProvider,
                lyricLines = lyricLines,
                translationLines = translationLines,
                isFocused = isFocused,
                isTransitioning = isTransitioning
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(if (isTransitioning) 1f else 0.895f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isTransitioning) Color.White else Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = if (isFocused || isTransitioning) Color.Transparent else Color.White,
                            shape = if (isTransitioning) RoundedCornerShape(0.dp) else RoundedCornerShape(24.dp)
                        )
                        .padding(if (isTransitioning) 4.dp else 1.dp)
                ) {
                Box(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 2.dp)
                            .size(28.dp)
                            .clickable(
                                onClick = { 
                                    currentEngineIndex = (currentEngineIndex + 1) % searchEngines.size
                                    sharedPrefs.edit().putInt("last_engine_index", currentEngineIndex).apply()
                                },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedContent(
                            targetState = loadBitmap,
                            transitionSpec = {
                                fadeIn(tween(220, delayMillis = 90)) togetherWith fadeOut(tween(90))
                            },
                            label = "LogoTransition"
                        ) { bitmap ->
                            if (bitmap != null) {
                                Image(bitmap = bitmap, contentDescription = selectedEngine.name,
                                    modifier = Modifier.fillMaxSize())
                            }
                        }
                    }

                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = {
                            Text("Search...", color = if (isFocused || isTransitioning) Color.Gray else Color.White.copy(0.6f))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (query.isNotBlank()) {
                                    isTransitioning = true
                                    val finalUrl = if (query.startsWith("http") || query.startsWith("www.") || (query.contains(".") && !query.contains(" "))) {
                                        if (query.startsWith("http")) query else "https://$query"
                                    } else {
                                        "${selectedEngine.searchUrl}${Uri.encode(query)}"
                                    }
                                    onSearch(finalUrl)
                                }
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { 
                                isFocused = it.isFocused 
                                onSearchFocusChanged(it.isFocused)
                            },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor   = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor  = Color.Transparent,
                            focusedIndicatorColor   = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor        = Color.Black,
                            unfocusedTextColor      = if (isTransitioning) Color.Black else Color.White,
                            cursorColor             = if (isFocused || isTransitioning) Color.Black else Color.White
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )

                    Button(
                        onClick = {
                            if (query.isNotBlank()) {
                                isTransitioning = true
                                val finalUrl = if (query.startsWith("http") || query.startsWith("www.") || (query.contains(".") && !query.contains(" "))) {
                                    if (query.startsWith("http")) query else "https://$query"
                                } else {
                                    "${selectedEngine.searchUrl}${Uri.encode(query)}"
                                }
                                onSearch(finalUrl)
                            }
                        },
                        modifier = Modifier.padding(end = 6.dp).size(40.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFocused || isTransitioning) Color.LightGray else Color.White.copy(0.3f),
                            contentColor   = if (isFocused || isTransitioning) Color.Black else Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (suggestions.isNotEmpty() && isFocused && !isTransitioning && query.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(0.98f).padding(top = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        LazyColumn(modifier = Modifier.height(240.dp)) {
                            items(suggestions) { suggestion ->
                                Column {
                                    Text(
                                        text = suggestion,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                query = suggestion
                                                onSearch("${selectedEngine.searchUrl}${Uri.encode(suggestion)}")
                                            }
                                            .padding(horizontal = 16.dp, vertical = 12.dp)
                                    )
                                    HorizontalDivider(color = Color.Black.copy(alpha = 0.05f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}@Composable
fun WallpaperPickerScreen(
    initialType: String,
    currentWallpaperPath: String,
    downloadProgress: MutableMap<String, Float>,
    downloadedPaths: MutableMap<String, String>,
    externalScope: CoroutineScope, // 持久作用域用于后台下载
    onUploadCustom: (Uri) -> Unit,
    onDismiss: () -> Unit,
    onSelectWallpaper: (type: String, path: String) -> Unit
) {
    val context = LocalContext.current
    val localScope = rememberCoroutineScope() // 本地作用域用于 UI 动画
    // 性能优化：不再在 Composable 内部创建 OkHttpClient 实例
    val client = remember { (context as? MainActivity)?.client ?: OkHttpClient() }
    val density = LocalDensity.current

    // 文件选择器
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onUploadCustom(it) }
    }

    // 入场动画：从屏幕底部滑入
    val slideIn = remember { Animatable(1f) } // 1f=屏幕底部, 0f=完全展示
    LaunchedEffect(Unit) {
        slideIn.animateTo(0f, tween(350, easing = FastOutSlowInEasing))
    }

    // VerticalPager: page0=静态, page1=动态
    val pagerState = rememberPagerState(
        initialPage = if (initialType == "static") 0 else 1,
        pageCount = { 2 }
    )

    // 封面位图缓存
    val coverBitmaps = remember { mutableStateMapOf<String, androidx.compose.ui.graphics.ImageBitmap?>() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            // 性能优化：使用 RGB_565 和降采样加载封面缩略图
            val coverOpts = BitmapFactory.Options().apply {
                inPreferredConfig = android.graphics.Bitmap.Config.RGB_565
                inSampleSize = 2  // 封面缩略图不需要全分辨率
            }
            (videoWallpapers + staticWallpapers).forEach { item ->
                val key = "${item.type}_${item.id}"
                val bmp = try {
                    context.assets.open(item.coverAsset).use { BitmapFactory.decodeStream(it, null, coverOpts)?.asImageBitmap() }
                } catch (e: Exception) {
                    try { context.assets.open("kaiping.png").use { BitmapFactory.decodeStream(it, null, coverOpts)?.asImageBitmap() } }
                    catch (e: Exception) { null }
                }
                withContext(Dispatchers.Main) { coverBitmaps[key] = bmp }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { translationY = slideIn.value * with(density) { 900.dp.toPx() } }
    ) {
        // 半透明黑色背景
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {}
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // ── 顶部标题栏 ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // 左上角加号上传按钮
                IconButton(
                    onClick = { launcher.launch("*/*") },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.Add, "上传壁纸", tint = Color.White)
                }

                Text(
                    text = if (pagerState.currentPage == 0) "静态壁纸" else "动态壁纸",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = { localScope.launch { slideIn.animateTo(1f, tween(300)); onDismiss() } },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(Icons.Default.Close, "关闭", tint = Color.White)
                }
            }

            // ── 双页垂直 Pager ──
            VerticalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> WallpaperSection(
                        items = staticWallpapers,
                        coverBitmaps = coverBitmaps,
                        downloadProgress = downloadProgress,
                        downloadedPaths = downloadedPaths,
                        bottomHint = true, // 底部提示↓动态
                        onDelete = { item ->
                            val key = "${item.type}_${item.id}"
                            val file = File(context.filesDir, "wallpapers/${item.type}/${item.fileName}")
                            val absolutePath = file.absolutePath
                            if (file.exists()) file.delete()
                            downloadedPaths.remove(key)
                            
                            // 如果删除的是当前正在使用的壁纸，重置为默认
                            if (currentWallpaperPath == absolutePath) {
                                onSelectWallpaper("static", "asset:///fengmian.png")
                            }
                        },
                        onTap = { item ->
                            val key = "${item.type}_${item.id}"
                            if (downloadedPaths.containsKey(key)) {
                                onSelectWallpaper("static", downloadedPaths[key]!!)
                            } else if (!downloadProgress.containsKey(key)) {
                                externalScope.launch {
                                    downloadProgress[key] = 0f
                                    val path = downloadWallpaper(context, item, client) { p ->
                                        downloadProgress[key] = p
                                    }
                                    if (path != null) {
                                        downloadedPaths[key] = path
                                        downloadProgress.remove(key)
                                        // 下载完成后自动应用并退出
                                        onSelectWallpaper("static", path)
                                    } else {
                                        downloadProgress.remove(key)
                                    }
                                }
                            }
                        }
                    )
                    1 -> WallpaperSection(
                        items = videoWallpapers,
                        coverBitmaps = coverBitmaps,
                        downloadProgress = downloadProgress,
                        downloadedPaths = downloadedPaths,
                        topHint = true, // 顶部提示↑静态
                        onDelete = { item ->
                            val key = "${item.type}_${item.id}"
                            val file = File(context.filesDir, "wallpapers/${item.type}/${item.fileName}")
                            val absolutePath = file.absolutePath
                            if (file.exists()) file.delete()
                            downloadedPaths.remove(key)

                            // 如果删除的是当前正在使用的壁纸，重置为默认
                            if (currentWallpaperPath == absolutePath) {
                                onSelectWallpaper("static", "asset:///fengmian.png")
                            }
                        },
                        onTap = { item ->
                            val key = "${item.type}_${item.id}"
                            if (downloadedPaths.containsKey(key)) {
                                onSelectWallpaper("video", downloadedPaths[key]!!)
                            } else if (!downloadProgress.containsKey(key)) {
                                externalScope.launch {
                                    downloadProgress[key] = 0f
                                    val path = downloadWallpaper(context, item, client) { p ->
                                        downloadProgress[key] = p
                                    }
                                    if (path != null) {
                                        downloadedPaths[key] = path
                                        downloadProgress.remove(key)
                                        // 下载完成后自动应用并退出
                                        onSelectWallpaper("video", path)
                                    } else {
                                        downloadProgress.remove(key)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}// ════════════════════════════════════════
//  单类型壁纸区域（动态 or 静态）
// ════════════════════════════════════════
@Composable
fun WallpaperSection(
    items: List<WallpaperItem>,
    coverBitmaps: Map<String, androidx.compose.ui.graphics.ImageBitmap?>,
    downloadProgress: Map<String, Float>,
    downloadedPaths: Map<String, String>,
    topHint: Boolean = false,   // 动态区域顶部显示 ↑静态壁纸
    bottomHint: Boolean = false, // 静态区域底部显示 ↓动态壁纸
    onDelete: (WallpaperItem) -> Unit,
    onTap: (WallpaperItem) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── 顶部占位/引导区 (固定高度确保卡片对齐) ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            contentAlignment = Alignment.Center
        ) {
            if (topHint) {
                val arrowAnim = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    while (true) {
                        arrowAnim.animateTo(-8f, tween(600, easing = FastOutSlowInEasing))
                        arrowAnim.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.offset { IntOffset(0, arrowAnim.value.toInt()) }
                ) {
                    Icon(Icons.Default.KeyboardArrowUp, null,
                        tint = Color.White.copy(0.8f), modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("静态壁纸", color = Color.White.copy(0.8f), fontSize = 13.sp)
                }
            }
        }

        // 横向 Pager：壁纸卡片 (占据剩余空间的 1f)
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 60.dp),
            pageSpacing = 12.dp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val item = items[page]
            val key = "${item.type}_${item.id}"
            val progress = downloadProgress[key]
            val isDownloaded = downloadedPaths.containsKey(key)
            val cover = coverBitmaps[key]

            val pageOffset = (pagerState.currentPage - page) +
                pagerState.currentPageOffsetFraction
            val scale = lerp(0.88f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(9f / 16f)
                    .graphicsLayer { scaleX = scale; scaleY = scale }
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onTap(item) },
                contentAlignment = Alignment.Center
            ) {
                if (cover != null) {
                    Image(
                        bitmap = cover,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(Modifier.fillMaxSize().background(Color.DarkGray))
                }

                if (isDownloaded && progress == null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .size(24.dp)
                            .background(Color.Red.copy(alpha = 0.8f), CircleShape)
                            .clickable { onDelete(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Clear, "删除", tint = Color.White, modifier = Modifier.size(14.dp))
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(24.dp)
                            .background(Color(0xFF4CAF50), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }

                if (progress != null) {
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.5f)))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.size(56.dp),
                            color = Color.White,
                            strokeWidth = 4.dp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("${(progress * 100).toInt()}%", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(3.dp).align(Alignment.BottomCenter),
                        color = Color.White,
                        trackColor = Color.White.copy(0.3f)
                    )
                }

                if (!isDownloaded && progress == null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.6f))))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("点击下载使用", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }

        // 页码指示点
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            repeat(items.size) { idx ->
                val selected = pagerState.currentPage == idx
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (selected) 8.dp else 5.dp)
                        .background(if (selected) Color.White else Color.White.copy(0.4f), CircleShape)
                )
            }
        }

        // ── 底部占位/引导区 (固定高度确保卡片对齐) ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (bottomHint) {
                val arrowAnim = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    while (true) {
                        arrowAnim.animateTo(8f, tween(600, easing = FastOutSlowInEasing))
                        arrowAnim.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.offset { IntOffset(0, arrowAnim.value.toInt()) }
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, null,
                        tint = Color.White.copy(0.6f), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("动态壁纸", color = Color.White.copy(0.6f), fontSize = 12.sp)
                }
            }
        }
    }
}// ── 历史记录页面 (毛玻璃模糊效果) ──
@Composable
fun HistoryScreen(
    historyList: List<HistoryItem>,
    onSelectUrl: (String) -> Unit,
    onDeleteItem: (HistoryItem) -> Unit,
    onClearAll: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var showClearConfirm by remember { mutableStateOf(false) }
    var clearCookiesChecked by remember { mutableStateOf(false) }

    // 默认使用深色/浅色覆盖层，不再重新渲染壁纸
    val contentColor = Color.Black // 默认黑色，可以根据需要调整

    BackHandler {
        onDismiss()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 模糊与半透明蒙层
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.85f) // 增加不透明度，确保在任何壁纸下都清晰
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = contentColor)
                }
                Text(
                    text = "历史记录",
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { showClearConfirm = true }) {
                    Icon(Icons.Default.DeleteSweep, "清空", tint = contentColor)
                }
            }

            if (historyList.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("暂无历史记录", color = contentColor.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(historyList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectUrl(item.url) },
                            colors = CardDefaults.cardColors(
                                containerColor = contentColor.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = contentColor,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = item.url,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = contentColor.copy(alpha = 0.6f),
                                        maxLines = 1
                                    )
                                }
                                IconButton(onClick = { onDeleteItem(item) }) {
                                    Icon(Icons.Default.Close, null, tint = contentColor.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 清空确认弹窗
        if (showClearConfirm) {
            androidx.compose.ui.window.Dialog(onDismissRequest = { showClearConfirm = false }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "清空历史",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.material3.Checkbox(
                                checked = clearCookiesChecked,
                                onCheckedChange = { clearCookiesChecked = it }
                            )
                            Text("同时清除 Cookies", color = Color.Black)
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showClearConfirm = false }) {
                                Text("取消")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    onClearAll(clearCookiesChecked)
                                    showClearConfirm = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("确认清空", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}// ── 书签页面 ──
@Composable
fun BookmarkScreen(
    bookmarksList: List<BookmarkItem>,
    onSelectUrl: (String) -> Unit,
    onDeleteItem: (BookmarkItem) -> Unit,
    onDismiss: () -> Unit
) {
    val contentColor = Color.Black

    BackHandler {
        onDismiss()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.85f)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = contentColor)
                }
                Text(
                    text = "书签",
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(48.dp)) // 占位保持对称
            }

            if (bookmarksList.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("暂无书签", color = contentColor.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bookmarksList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectUrl(item.url) },
                            colors = CardDefaults.cardColors(
                                containerColor = contentColor.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = contentColor,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = item.url,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = contentColor.copy(alpha = 0.6f),
                                        maxLines = 1
                                    )
                                }
                                IconButton(onClick = { onDeleteItem(item) }) {
                                    Icon(Icons.Default.Close, null, tint = contentColor.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}// ── 下载页面 ──
@Composable
fun DownloadScreen(
    downloadsList: MutableList<DownloadItem>,
    downloadScope: CoroutineScope,
    okHttpClient: OkHttpClient,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val contentColor = Color.Black

    BackHandler {
        onDismiss()
    }

    // 分类任务：进行中包含 Downloading 和 Paused
    val downloadingTasks = downloadsList.filter { it.status == "Downloading" || it.status == "Paused" }
    val completedTasks = downloadsList.filter { it.status == "Completed" || it.status == "Failed" }

    var selectedItemForMenu by remember { mutableStateOf<DownloadItem?>(null) }
    var menuOffset by remember { mutableStateOf(DpOffset.Zero) }
    val density = LocalDensity.current

    // 删除源文件确认弹窗状态
    var showDeleteFileDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<DownloadItem?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.85f)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = contentColor)
                }
                Text(
                    text = "下载管理",
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            if (downloadsList.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("暂无下载内容", color = contentColor.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (downloadingTasks.isNotEmpty()) {
                        item {
                            Text("进行中 (${downloadingTasks.size})", 
                                style = MaterialTheme.typography.bodySmall, 
                                color = contentColor.copy(alpha = 0.5f),
                                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp))
                        }
                        items(downloadingTasks) { item ->
                            var pressOffset by remember { mutableStateOf<DpOffset?>(null) }
                            Box {
                                DownloadTaskItem(
                                    item = item,
                                    contentColor = contentColor,
                                    onLongPress = { offset -> 
                                        selectedItemForMenu = item
                                        pressOffset = with(density) { DpOffset(offset.x.toDp(), offset.y.toDp()) }
                                    },
                                    onDeleteClick = {
                                        itemToDelete = item
                                        showDeleteFileDialog = true
                                    }
                                )
                                
                                if (selectedItemForMenu == item && pressOffset != null) {
                                    Box(
                                        modifier = Modifier
                                            .offset(x = pressOffset!!.x, y = pressOffset!!.y)
                                            .size(0.dp)
                                    ) {
                                        DropdownMenu(
                                            expanded = true,
                                            onDismissRequest = { 
                                                selectedItemForMenu = null 
                                                pressOffset = null
                                            }
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("复制下载链接") },
                                                onClick = {
                                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                    val clip = ClipData.newPlainText("url", item.url)
                                                    clipboard.setPrimaryClip(clip)
                                                    android.widget.Toast.makeText(context, "链接已复制", android.widget.Toast.LENGTH_SHORT).show()
                                                    selectedItemForMenu = null
                                                    pressOffset = null
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("删除任务", color = Color.Red) },
                                                onClick = {
                                                    selectedItemForMenu = null
                                                    pressOffset = null
                                                    itemToDelete = item
                                                    showDeleteFileDialog = true
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (completedTasks.isNotEmpty()) {
                        item {
                            Spacer(Modifier.height(8.dp))
                            Text("已完成 (${completedTasks.size})", 
                                style = MaterialTheme.typography.bodySmall, 
                                color = contentColor.copy(alpha = 0.5f),
                                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp))
                        }
                        items(completedTasks) { item ->
                            var pressOffset by remember { mutableStateOf<DpOffset?>(null) }
                            Box {
                                DownloadTaskItem(
                                    item = item,
                                    contentColor = contentColor,
                                    onClick = {
                                        if (item.status == "Completed") {
                                            openDownloadedFile(context, item.fileName)
                                        }
                                    },
                                    onLongPress = { offset ->
                                        selectedItemForMenu = item
                                        pressOffset = with(density) { DpOffset(offset.x.toDp(), offset.y.toDp()) }
                                    }
                                )
                                
                                if (selectedItemForMenu == item && pressOffset != null) {
                                    Box(
                                        modifier = Modifier
                                            .offset(x = pressOffset!!.x, y = pressOffset!!.y)
                                            .size(0.dp)
                                    ) {
                                        DropdownMenu(
                                            expanded = true,
                                            onDismissRequest = { 
                                                selectedItemForMenu = null 
                                                pressOffset = null
                                            }
                                        ) {
                                            if (item.status == "Completed") {
                                                DropdownMenuItem(
                                                    text = { Text("打开文件") },
                                                    onClick = {
                                                        openDownloadedFile(context, item.fileName)
                                                        selectedItemForMenu = null
                                                        pressOffset = null
                                                    }
                                                )
                                            }
                                            DropdownMenuItem(
                                                text = { Text("复制下载链接") },
                                                onClick = {
                                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                    val clip = ClipData.newPlainText("url", item.url)
                                                    clipboard.setPrimaryClip(clip)
                                                    android.widget.Toast.makeText(context, "链接已复制", android.widget.Toast.LENGTH_SHORT).show()
                                                    selectedItemForMenu = null
                                                    pressOffset = null
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("删除任务", color = Color.Red) },
                                                onClick = {
                                                    selectedItemForMenu = null
                                                    pressOffset = null
                                                    itemToDelete = item
                                                    showDeleteFileDialog = true
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 删除源文件确认弹窗
        if (showDeleteFileDialog && itemToDelete != null) {
            androidx.compose.ui.window.Dialog(onDismissRequest = {
                showDeleteFileDialog = false
                itemToDelete = null
            }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "删除任务",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "是否同时删除已下载的源文件？",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )
                        Text(
                            text = itemToDelete!!.fileName,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = {
                                // 仅删除任务记录，保留文件
                                val theItem = itemToDelete!!
                                if (theItem.status == "Downloading" && theItem.downloadId != -1L) {
                                    activeDownloadJobs[theItem.downloadId]?.cancel()
                                    activeDownloadJobs.remove(theItem.downloadId)
                                } else if (theItem.status == "Paused" && theItem.downloadId != -1L) {
                                    activeDownloadJobs.remove(theItem.downloadId)
                                }
                                cancelDownloadNotification(context, theItem.downloadId)
                                downloadsList.remove(theItem)
                                showDeleteFileDialog = false
                                itemToDelete = null
                            }) {
                                Text("仅删除任务")
                            }
                            Button(
                                onClick = {
                                    // 同时删除任务和源文件
                                    val theItem = itemToDelete!!
                                    if (theItem.status == "Downloading" && theItem.downloadId != -1L) {
                                        activeDownloadJobs[theItem.downloadId]?.cancel()
                                        activeDownloadJobs.remove(theItem.downloadId)
                                    } else if (theItem.status == "Paused" && theItem.downloadId != -1L) {
                                        activeDownloadJobs.remove(theItem.downloadId)
                                    }
                                    cancelDownloadNotification(context, theItem.downloadId)
                                    downloadsList.remove(theItem)
                                    try {
                                        if (checkDownloadFileExists(context, theItem.fileName)) {
                                            deleteDownloadedFile(context, false, theItem.fileName)
                                            android.widget.Toast.makeText(context, "文件已删除", android.widget.Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        android.widget.Toast.makeText(context, "删除文件失败", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                    showDeleteFileDialog = false
                                    itemToDelete = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                            ) {
                                Text("删除文件", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun DownloadTaskItem(
    item: DownloadItem,
    contentColor: Color,
    onClick: (() -> Unit)? = null,
    onLongPress: (Offset) -> Unit,
    onDeleteClick: (() -> Unit)? = null
) {
    val sizeLine = if (item.totalBytes > 0L) {
        "${formatDownloadBytes(item.downloadedBytes)} / ${formatDownloadBytes(item.totalBytes)}"
    } else if (item.downloadedBytes > 0L) {
        "${formatDownloadBytes(item.downloadedBytes)} / ${item.size}"
    } else {
        item.size
    }
    val statusText = when (item.status) {
        "Completed" -> "已完成"
        "Failed" -> item.speed.ifBlank { "下载失败" }
        "Paused" -> "已暂停"
        else -> item.speed.ifBlank { "下载中" }
    }
    val progressPercent = (item.progress.coerceIn(0f, 1f) * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick?.invoke() },
                    onLongPress = { offset -> onLongPress(offset) }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = contentColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = when (item.status) {
                        "Completed" -> Icons.Default.Check
                        "Failed" -> Icons.Default.Close
                        "Paused" -> Icons.Default.Pause
                        else -> Icons.Default.Download
                    },
                    contentDescription = null,
                    tint = when (item.status) {
                        "Completed" -> Color(0xFF4CAF50)
                        "Failed" -> Color(0xFFE53935)
                        "Paused" -> Color(0xFFFFA726)
                        else -> contentColor
                    },
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.fileName,
                        style = MaterialTheme.typography.titleMedium,
                        color = contentColor,
                        maxLines = 1
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = sizeLine,
                            style = MaterialTheme.typography.bodySmall,
                            color = contentColor.copy(alpha = 0.6f)
                        )
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.bodySmall,
                            color = when (item.status) {
                                "Failed" -> Color(0xFFE53935)
                                "Paused" -> Color(0xFFFFA726)
                                else -> contentColor.copy(alpha = 0.8f)
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                // 删除任务按钮
                if ((item.status == "Downloading" || item.status == "Paused") && onDeleteClick != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "删除任务",
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            if (item.status == "Downloading" || item.status == "Paused") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "进度",
                        style = MaterialTheme.typography.bodySmall,
                        color = contentColor.copy(alpha = 0.55f)
                    )
                    Text(
                        text = "$progressPercent%",
                        style = MaterialTheme.typography.bodySmall,
                        color = contentColor.copy(alpha = 0.75f),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { item.progress.coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                    color = if (item.status == "Paused") Color(0xFFFFA726) else contentColor,
                    trackColor = contentColor.copy(alpha = 0.2f)
                )
            }
        }
    }
}// ════════════════════════════════════════
//  设置页面
// ════════════════════════════════════════
@Composable
fun SettingsScreen(
    isNightMode: Boolean,
    fontSize: Int,
    onFontSizeChange: (Int) -> Unit,
    isDesktopMode: Boolean,
    onToggleDesktopMode: () -> Unit,
    onToggleNightMode: () -> Unit,
    onOpenCookiesSettings: () -> Unit,
    onOpenDownloadSettings: () -> Unit,
    onClearCache: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val contentColor = Color.Black
    val coroutineScope = rememberCoroutineScope()
    var cacheSizeStr by remember { mutableStateOf("正在计算...") }
    var showClearCacheConfirmDialog by remember { mutableStateOf(false) }

    // Helper functions for size calculation and deletion
    fun getFolderSize(file: java.io.File?): Long {
        if (file == null || !file.exists()) return 0L
        if (!file.isDirectory) return file.length()
        var size = 0L
        val files = file.listFiles()
        if (files != null) {
            for (f in files) {
                size += getFolderSize(f)
            }
        }
        return size
    }

    fun deleteFolder(file: java.io.File?): Boolean {
        if (file == null || !file.exists()) return true
        var success = true
        if (file.isDirectory) {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    success = success && deleteFolder(f)
                }
            }
        }
        success = success && file.delete()
        return success
    }

    fun formatSize(size: Long): String {
        if (size <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt().coerceIn(0, units.size - 1)
        return String.format(java.util.Locale.US, "%.2f %s", size / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
    }

    fun calculateCacheSize() {
        coroutineScope.launch(Dispatchers.IO) {
            var size = getFolderSize(context.cacheDir) + getFolderSize(context.codeCacheDir)
            try {
                context.externalCacheDir?.let {
                    size += getFolderSize(it)
                }
            } catch (e: Exception) {
                // ignore
            }
            val formatted = formatSize(size)
            withContext(Dispatchers.Main) {
                cacheSizeStr = formatted
            }
        }
    }

    LaunchedEffect(Unit) {
        calculateCacheSize()
    }

    BackHandler { onDismiss() }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.85f)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = contentColor)
                }
                Text(
                    text = "设置",
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 字体大小
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("字体大小", color = contentColor, fontWeight = FontWeight.Medium)
                        Text("${fontSize}%", color = contentColor.copy(alpha = 0.7f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = fontSize.toFloat(),
                        onValueChange = { onFontSizeChange(it.toInt()) },
                        valueRange = 50f..200f,
                        steps = 14,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("A", fontSize = 12.sp, color = contentColor.copy(alpha = 0.5f))
                        Text("A", fontSize = 20.sp, color = contentColor.copy(alpha = 0.5f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 夜间模式开关
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (isNightMode) Icons.Default.LightMode else Icons.Default.NightsStay,
                            contentDescription = null,
                            tint = contentColor, modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(if (isNightMode) "白天模式" else "夜间模式", color = contentColor)
                    }
                    Switch(checked = isNightMode, onCheckedChange = { onToggleNightMode() })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 桌面网站开关
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (isDesktopMode) Icons.Default.PhoneAndroid else Icons.Default.Computer,
                            contentDescription = null,
                            tint = contentColor, modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(if (isDesktopMode) "手机版网站" else "电脑版网站", color = contentColor)
                    }
                    Switch(checked = isDesktopMode, onCheckedChange = { onToggleDesktopMode() })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 下载设置
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenDownloadSettings() },
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Download, contentDescription = null, tint = contentColor, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(12.dp))
                        Text("下载设置", color = contentColor)
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = contentColor.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cookies 管理
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenCookiesSettings() },
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = contentColor, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(12.dp))
                        Text("Cookies 管理", color = contentColor)
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = contentColor.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 清除缓存
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showClearCacheConfirmDialog = true
                    },
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = contentColor, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("清除浏览缓存 ($cacheSizeStr)", color = contentColor)
                }
            }
        }

        // 清除缓存确认弹窗
        if (showClearCacheConfirmDialog) {
            androidx.compose.ui.window.Dialog(onDismissRequest = { showClearCacheConfirmDialog = false }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "清除浏览缓存",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "清除缓存后将会删除所有网站的登录状态并清空本地临时缓存文件，确定要删除吗？",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showClearCacheConfirmDialog = false }) {
                                Text("取消", color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(
                                onClick = {
                                    showClearCacheConfirmDialog = false
                                    coroutineScope.launch {
                                        onClearCache()
                                        withContext(Dispatchers.IO) {
                                            deleteFolder(context.cacheDir)
                                            deleteFolder(context.codeCacheDir)
                                            try {
                                                context.externalCacheDir?.let {
                                                    deleteFolder(it)
                                                }
                                            } catch (e: Exception) {
                                                // ignore
                                            }
                                        }
                                        calculateCacheSize()
                                        android.widget.Toast.makeText(context, "缓存已彻底清空", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("确定", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadSettingsDialog(
    currentMusicPath: String,
    currentFilePath: String,
    onChooseMusicFolder: () -> Unit,
    onChooseFileFolder: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "下载设置",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // 音乐下载位置
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChooseMusicFolder() }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "音乐下载位置",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentMusicPath,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(16.dp))
                
                // 文件下载位置
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChooseFileFolder() }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "文件下载位置",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentFilePath,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("关闭", color = Color(0xFF007AFF), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}// ── Cookies 管理页面 ──
@Composable
fun CookiesManagementScreen(
    allowCookies: Boolean,
    onToggleAllowCookies: (Boolean) -> Unit,
    cookieDomains: MutableList<String>,
    onDeleteDomain: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val contentColor = Color.Black

    BackHandler { onDismiss() }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.85f)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回", tint = contentColor)
                }
                Text(
                    text = "Cookies 管理",
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 允许 Cookies 开关
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("允许网站保存 Cookies", color = contentColor, fontWeight = FontWeight.Medium)
                        Text("关闭后网站将无法记住您的登录状态", style = MaterialTheme.typography.bodySmall, color = contentColor.copy(alpha = 0.6f))
                    }
                    Switch(checked = allowCookies, onCheckedChange = { onToggleAllowCookies(it) })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("已保存 Cookies 的网站", style = MaterialTheme.typography.titleMedium, color = contentColor)
            Spacer(modifier = Modifier.height(12.dp))

            if (cookieDomains.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("暂无数据", color = contentColor.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cookieDomains) { domain ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = contentColor.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Icon(Icons.Default.Search, null, tint = contentColor.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(12.dp))
                                    Text(domain, color = contentColor, maxLines = 1)
                                }
                                IconButton(onClick = { onDeleteDomain(domain) }, modifier = Modifier.size(24.dp)) {
                                    Icon(Icons.Default.Close, "删除", tint = contentColor.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── 音乐播放器 BottomSheet 面板组件 ──
@Composable
fun LyricBlock(
    line: LyricLine?,
    isCurrent: Boolean,
    isNightMode: Boolean,
    translationText: String?
) {
    if (line == null) {
        Spacer(modifier = Modifier.height(24.dp))
        return
    }
    val textColor = if (isCurrent) {
        if (isNightMode) Color.White else Color(0xFF007AFF)
    } else {
        if (isNightMode) Color.Gray.copy(alpha = 0.6f) else Color.Gray
    }
    val origSize = if (isCurrent) 14.sp else 11.sp
    val transSize = if (isCurrent) 12.sp else 9.sp
    val fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
    ) {
        Text(
            text = line.text,
            color = textColor,
            fontSize = origSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
        if (translationText != null) {
            Text(
                text = translationText,
                color = textColor.copy(alpha = 0.8f),
                fontSize = transSize,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSec = ms / 1000
    val min = totalSec / 60
    val sec = totalSec % 60
    return String.format("%02d:%02d", min, sec)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicOverlay(
    visible: Boolean,
    isNightMode: Boolean = false,
    onDismiss: () -> Unit,
    musicList: SnapshotStateList<MusicItem>,
    currentPlayingSong: MusicItem?,
    onCurrentPlayingSongChange: (MusicItem?) -> Unit,
    isMusicPlaying: Boolean,
    onIsMusicPlayingChange: (Boolean) -> Unit,
    musicPosition: Long,
    onMusicPositionChange: (Long) -> Unit,
    musicDuration: Long,
    musicService: MusicService?,
    lyricLines: List<LyricLine>,
    translationLines: List<LyricLine>,
    client: OkHttpClient
) {
    val musicPlayer = musicService?.musicPlayer
    val density = LocalDensity.current
    val sheetHeightPx = remember(density) { with(density) { 440.dp.toPx() } }

    var shouldRender by remember { mutableStateOf(visible) }
    if (visible) {
        shouldRender = true
    }
    val offsetY   = remember { Animatable(sheetHeightPx) }
    val dimAlpha  = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var isPlaylistOpen by remember { mutableStateOf(false) }
    var songToDelete by remember { mutableStateOf<MusicItem?>(null) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    // Animated values for layout transitions between open and closed playlist
    val playerTranslationX by animateDpAsState(targetValue = if (isPlaylistOpen) (-90).dp else 0.dp, animationSpec = tween(300), label = "PlayerTranslationX")
    val playlistTranslationX by animateDpAsState(targetValue = if (isPlaylistOpen) 0.dp else 180.dp, animationSpec = tween(300), label = "PlaylistTranslationX")
    val progressPadding by animateDpAsState(targetValue = if (isPlaylistOpen) 90.dp else 32.dp, animationSpec = tween(300), label = "ProgressPadding")

    val discSize = if (isPlaylistOpen) 72.dp else 110.dp
    val titleSize = if (isPlaylistOpen) 14f else 18f
    val artistSize = if (isPlaylistOpen) 11f else 14f
    val buttonSpacing = if (isPlaylistOpen) 8.dp else 24.dp

    val prevNextButtonSize = if (isPlaylistOpen) 36.dp else 48.dp
    val prevNextIconSize = if (isPlaylistOpen) 22.dp else 28.dp
    val playPauseButtonSize = if (isPlaylistOpen) 44.dp else 56.dp
    val playPauseIconSize = if (isPlaylistOpen) 30.dp else 36.dp
    val stopButtonSize = if (isPlaylistOpen) 32.dp else 44.dp
    val stopIconSize = if (isPlaylistOpen) 18.dp else 24.dp
    val spacerBelowDisc = if (isPlaylistOpen) 4.dp else 8.dp
    val spacerBelowTitle = if (isPlaylistOpen) 4.dp else 8.dp
    val spacerBelowLyrics = if (isPlaylistOpen) 6.dp else 10.dp
    val spacerBelowProgress = if (isPlaylistOpen) 4.dp else 6.dp
    val bottomPadding = if (isPlaylistOpen) 16.dp else 4.dp

    LaunchedEffect(visible) {
        if (visible) {
            shouldRender = true
            launch { offsetY.animateTo(0f, tween(300, easing = FastOutSlowInEasing)) }
            dimAlpha.animateTo(0.55f, tween(300))
        } else {
            launch { offsetY.animateTo(sheetHeightPx, tween(250, easing = FastOutSlowInEasing)) }
            dimAlpha.animateTo(0f, tween(250))
            delay(250)
            shouldRender = false
        }
    }

    if (!shouldRender) return

    val panelBg = if (isNightMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isNightMode) Color.White else Color.Black

    // 从 assets 中加载 changpian.png
    val bitmap = remember {
        try {
            context.assets.open("logo/changpian.png").use {
                android.graphics.BitmapFactory.decodeStream(it)
            }?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 碟片旋转角度
    var rotationAngle by remember { mutableStateOf(0f) }
    LaunchedEffect(isMusicPlaying, visible) {
        if (isMusicPlaying && visible) {
            var lastTime = System.nanoTime()
            while (true) {
                withFrameNanos { time ->
                    val elapsedSeconds = (time - lastTime) / 1_000_000_000f
                    rotationAngle = (rotationAngle + elapsedSeconds * 24f) % 360f
                    lastTime = time
                }
            }
        }
    }

    // 播放下一首
    fun playNext() {
        val downloadedList = musicList.filter { it.isDownloaded }
        if (downloadedList.isEmpty()) return
        val currIndex = downloadedList.indexOfFirst { it.id == currentPlayingSong?.id }
        val nextSong = if (currIndex != -1) {
            downloadedList[(currIndex + 1) % downloadedList.size]
        } else {
            downloadedList.first()
        }
        onCurrentPlayingSongChange(nextSong)
        musicService?.playSong(nextSong, downloadedList)
    }

    // 播放上一首
    fun playPrev() {
        val downloadedList = musicList.filter { it.isDownloaded }
        if (downloadedList.isEmpty()) return
        val currIndex = downloadedList.indexOfFirst { it.id == currentPlayingSong?.id }
        val prevSong = if (currIndex != -1) {
            downloadedList[(currIndex - 1 + downloadedList.size) % downloadedList.size]
        } else {
            downloadedList.last()
        }
        onCurrentPlayingSongChange(prevSong)
        musicService?.playSong(prevSong, downloadedList)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showDeleteConfirmDialog && songToDelete != null) {
            val song = songToDelete!!
            AlertDialog(
                onDismissRequest = {
                    showDeleteConfirmDialog = false
                    songToDelete = null
                },
                title = {
                    Text(text = "确认删除", color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                },
                text = {
                    Text(text = "确定要删除歌曲《${song.title}》及其歌词吗？", color = textColor, fontSize = 14.sp)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val deleted = deleteMusicFile(context, song.filename, song.hasTranslation)
                            if (deleted) {
                                val idx = musicList.indexOfFirst { it.id == song.id }
                                if (idx != -1) {
                                    musicList[idx] = musicList[idx].copy(
                                        isDownloaded = false,
                                        downloadProgress = -1f
                                    )
                                }
                                if (currentPlayingSong?.id == song.id) {
                                    onIsMusicPlayingChange(false)
                                    musicService?.stopSong()
                                    onCurrentPlayingSongChange(null)
                                }
                                android.widget.Toast.makeText(context, "删除成功", android.widget.Toast.LENGTH_SHORT).show()
                            } else {
                                android.widget.Toast.makeText(context, "删除失败", android.widget.Toast.LENGTH_SHORT).show()
                            }
                            showDeleteConfirmDialog = false
                            songToDelete = null
                        }
                    ) {
                        Text(text = "确定", color = if (isNightMode) Color.White else Color(0xFF007AFF))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteConfirmDialog = false
                            songToDelete = null
                        }
                    ) {
                        Text(text = "取消", color = textColor.copy(alpha = 0.6f))
                    }
                },
                containerColor = panelBg
            )
        }

        // 背景遮罩
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 1f }
                .drawBehind {
                    drawRect(Color.Black.copy(alpha = dimAlpha.value))
                }
                .then(
                    if (visible) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onDismiss
                        )
                    } else {
                        Modifier
                    }
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(440.dp)
                .graphicsLayer { translationY = offsetY.value }
                .background(panelBg, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .navigationBarsPadding()
                .then(
                    if (visible) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {}
                    } else {
                        Modifier
                    }
                )
        ) {
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // --- Left Column: Player Panel ---
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .graphicsLayer {
                            translationX = with(density) { playerTranslationX.toPx() }
                        }
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(12.dp))

                    // 1. Album art (size discSize animates smoothly!)
                    Box(
                        modifier = Modifier
                            .size(discSize)
                            .clip(CircleShape)
                            .background(textColor.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = "唱片",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer { rotationZ = rotationAngle }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = null,
                                tint = textColor.copy(alpha = 0.6f),
                                modifier = Modifier
                                    .size(discSize * 0.5f)
                                    .graphicsLayer { rotationZ = rotationAngle }
                            )
                        }
                    }

                    Spacer(Modifier.height(spacerBelowDisc))

                    // 2. Song Title & Artist (font sizes animate smoothly!)
                    Text(
                        text = currentPlayingSong?.title ?: "未播放",
                        color = textColor,
                        fontSize = titleSize.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = currentPlayingSong?.artist ?: "未知歌手",
                        color = textColor.copy(alpha = 0.6f),
                        fontSize = artistSize.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(spacerBelowTitle))

                    // 3. Lyrics (scrolling block in open state, single active lyric with translation in closed state)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isPlaylistOpen) {
                            // --- Playlist Open: 3-line scrolling block ---
                            if (lyricLines.isNotEmpty()) {
                                val currentLyricIndex = remember(lyricLines.size, musicPosition) {
                                    val idx = lyricLines.indexOfLast { it.timeMs <= musicPosition + 300 }
                                    if (idx == -1) 0 else idx
                                }
                                AnimatedContent(
                                    targetState = currentLyricIndex,
                                    transitionSpec = {
                                        (slideInVertically { height -> height } + fadeIn())
                                            .togetherWith(slideOutVertically { height -> -height } + fadeOut())
                                    },
                                    label = "MusicOverlayLyricsAnimation"
                                ) { targetIndex ->
                                    val targetPrev = lyricLines.getOrNull(targetIndex - 1)
                                    val targetCur = lyricLines.getOrNull(targetIndex)
                                    val targetNext = lyricLines.getOrNull(targetIndex + 1)
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        LyricBlock(line = targetPrev, isCurrent = false, isNightMode = isNightMode, translationText = null)
                                        LyricBlock(line = targetCur, isCurrent = true, isNightMode = isNightMode, translationText = null)
                                        LyricBlock(line = targetNext, isCurrent = false, isNightMode = isNightMode, translationText = null)
                                    }
                                }
                            } else {
                                Text(
                                    text = "无歌词",
                                    color = textColor.copy(alpha = 0.4f),
                                    fontSize = 13.sp
                                )
                            }
                        } else {
                            // --- Playlist Closed: Active lyric with translation (multi-line allowed!) ---
                            if (lyricLines.isNotEmpty()) {
                                val currentLyricIndex = remember(lyricLines.size, musicPosition) {
                                    lyricLines.indexOfLast { it.timeMs <= musicPosition + 300 }
                                }
                                val currentTranslationIndex = remember(translationLines.size, musicPosition) {
                                    translationLines.indexOfLast { it.timeMs <= musicPosition + 300 }
                                }
                                val targetLine = if (currentLyricIndex != -1) lyricLines.getOrNull(currentLyricIndex) else null
                                val targetTrans = if (currentTranslationIndex != -1) translationLines.getOrNull(currentTranslationIndex)?.text else null

                                if (targetLine != null || !targetTrans.isNullOrBlank()) {
                                    val shadowStyle = TextStyle(
                                        shadow = androidx.compose.ui.graphics.Shadow(
                                            color = Color.Black.copy(alpha = 0.8f),
                                            offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                                            blurRadius = 6f
                                        )
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        if (targetLine != null) {
                                            Text(
                                                text = targetLine.text,
                                                color = if (isNightMode) Color.White else Color(0xFF007AFF),
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                style = shadowStyle
                                            )
                                        }
                                        if (!targetTrans.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = targetTrans,
                                                color = if (isNightMode) Color.White else Color(0xFF007AFF),
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Normal,
                                                textAlign = TextAlign.Center,
                                                style = shadowStyle
                                            )
                                        }
                                    }
                                }
                            } else {
                                Text(
                                    text = "无歌词",
                                    color = textColor.copy(alpha = 0.4f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(spacerBelowLyrics))

                    // 4. Progress bar (side padding progressPadding animates smoothly!)
                    var sliderValue by remember { mutableStateOf<Float?>(null) }
                    val currentPos = sliderValue?.toLong() ?: musicPosition
                    val duration = musicDuration

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = progressPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isPlaylistOpen) {
                            Text(
                                text = formatTime(currentPos),
                                fontSize = 9.sp,
                                color = textColor.copy(alpha = 0.6f)
                            )
                        }
                        Slider(
                            value = if (duration > 0) currentPos.toFloat() / duration else 0f,
                            onValueChange = {
                                sliderValue = it * duration
                            },
                            onValueChangeFinished = {
                                sliderValue?.let {
                                    musicPlayer?.seekTo(it.toLong())
                                    onMusicPositionChange(it.toLong())
                                }
                                sliderValue = null
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(32.dp)
                                .padding(horizontal = if (isPlaylistOpen) 0.dp else 8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = if (isNightMode) Color.White else Color(0xFF007AFF),
                                activeTrackColor = if (isNightMode) Color.White else Color(0xFF007AFF),
                                inactiveTrackColor = textColor.copy(alpha = 0.2f)
                            )
                        )
                        if (!isPlaylistOpen) {
                            Text(
                                text = formatTime(duration),
                                fontSize = 9.sp,
                                color = textColor.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(Modifier.height(spacerBelowProgress))

                    // 5. Symmetric centered playback controls (spacing buttonSpacing animates smoothly!)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = ::playPrev, modifier = Modifier.size(prevNextButtonSize)) {
                            Icon(
                                Icons.Default.SkipPrevious,
                                "上一首",
                                tint = textColor,
                                modifier = Modifier.size(prevNextIconSize)
                            )
                        }

                        Spacer(Modifier.width(buttonSpacing))

                        IconButton(
                            onClick = {
                                if (currentPlayingSong == null) {
                                    val downloaded = musicList.filter { it.isDownloaded }
                                    if (downloaded.isNotEmpty()) {
                                        val firstSong = downloaded.first()
                                        onCurrentPlayingSongChange(firstSong)
                                        musicService?.playSong(firstSong, downloaded)
                                    } else {
                                        android.widget.Toast.makeText(context, "请先下载歌曲", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    if (isMusicPlaying) {
                                        onIsMusicPlayingChange(false)
                                        musicService?.pauseSong()
                                    } else {
                                        onIsMusicPlayingChange(true)
                                        musicService?.resumeSong()
                                    }
                                }
                            },
                            modifier = Modifier.size(playPauseButtonSize)
                        ) {
                            Icon(
                                imageVector = if (isMusicPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "播放/暂停",
                                tint = textColor,
                                modifier = Modifier.size(playPauseIconSize)
                            )
                        }

                        Spacer(Modifier.width(buttonSpacing))

                        IconButton(onClick = ::playNext, modifier = Modifier.size(prevNextButtonSize)) {
                            Icon(
                                Icons.Default.SkipNext,
                                "下一首",
                                tint = textColor,
                                modifier = Modifier.size(prevNextIconSize)
                            )
                        }

                        Spacer(Modifier.width(buttonSpacing))

                        IconButton(
                            onClick = {
                                onIsMusicPlayingChange(false)
                                musicService?.stopSong()
                                onMusicPositionChange(0L)
                            },
                            modifier = Modifier.size(stopButtonSize)
                        ) {
                            Icon(
                                Icons.Default.Stop,
                                "停止",
                                tint = textColor.copy(alpha = 0.7f),
                                modifier = Modifier.size(stopIconSize)
                            )
                        }
                    }

                    Spacer(Modifier.height(bottomPadding))
                }

                // Toggle Handle Button (aligned to right, slides with the playlist)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .graphicsLayer {
                            translationX = with(density) { (-180.dp + playlistTranslationX).toPx() }
                        }
                        .width(36.dp)
                        .fillMaxHeight()
                        .clickable(
                            onClick = { isPlaylistOpen = !isPlaylistOpen },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaylistOpen) Icons.Default.KeyboardArrowRight else Icons.Default.KeyboardArrowLeft,
                        contentDescription = "切换播放列表",
                        tint = textColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Playlist Box (aligned to right, fixed width 180.dp, graphicsLayer translationX)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(180.dp)
                        .fillMaxHeight()
                        .graphicsLayer {
                            translationX = with(density) { playlistTranslationX.toPx() }
                        }
                        .background(panelBg)
                        .clipToBounds()
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        // Fixed Vertical Divider Line
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(textColor.copy(alpha = 0.1f))
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "播放列表 (${musicList.size}首)",
                                color = textColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
                            )

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(musicList, key = { it.id }) { item ->
                                    val isCurrent = currentPlayingSong?.id == item.id
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                if (isCurrent) textColor.copy(alpha = 0.1f) else Color.Transparent
                                            )
                                            .combinedClickable(
                                                onClick = {
                                                    if (item.isDownloaded) {
                                                        onCurrentPlayingSongChange(item)
                                                        val downloadedList = musicList.filter { it.isDownloaded }
                                                        musicService?.playSong(item, downloadedList)
                                                    } else if (item.downloadProgress == -1f) {
                                                        downloadMusicItem(context, item, musicList, client, scope)
                                                    }
                                                },
                                                onLongClick = {
                                                    if (item.isDownloaded) {
                                                        songToDelete = item
                                                        showDeleteConfirmDialog = true
                                                    }
                                                }
                                            )
                                            .padding(vertical = 6.dp, horizontal = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "${item.id}. ${item.title}",
                                                color = if (isCurrent) {
                                                    if (isNightMode) Color.White else Color(0xFF007AFF)
                                                } else textColor,
                                                fontSize = 11.sp,
                                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = item.artist,
                                                color = textColor.copy(alpha = 0.5f),
                                                fontSize = 9.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        Spacer(Modifier.width(4.dp))

                                        if (item.isDownloaded) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "已下载",
                                                tint = Color(0xFF4CAF50),
                                                modifier = Modifier.size(13.dp)
                                            )
                                        } else if (item.downloadProgress >= 0f && item.downloadProgress <= 1f) {
                                            val percent = (item.downloadProgress * 100).toInt()
                                            Text(
                                                text = "$percent%",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.Download,
                                                contentDescription = "点击下载",
                                                tint = textColor.copy(alpha = 0.4f),
                                                modifier = Modifier.size(13.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}