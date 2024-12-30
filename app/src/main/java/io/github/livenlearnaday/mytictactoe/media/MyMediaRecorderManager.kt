package io.github.livenlearnaday.mytictactoe.media

import android.app.Service.MEDIA_PROJECTION_SERVICE
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


object MyMediaRecorderManager {

    private const val TAG = "MyMediaRecorderManager"
    private const val BIT_RATE = 500 * 1000
    private const val FRAME_RATE = 30
    const val FILE_FORMAT_VIDEO = ".mp4"
    private const val FILE_FOLDER = "/mytictactoe"
    private const val SEPARATOR = "/"



    private var mMediaRecorder: MediaRecorder? = null

    var fileName: String = ""

    var filePath: String = ""

    var filePathPrefix: String = Environment.getExternalStoragePublicDirectory(
        Environment
            .DIRECTORY_DOWNLOADS
    )
        .toString() + StringBuilder(FILE_FOLDER)
        .append(SEPARATOR)

    var isMediaRecorderError: Boolean = false

    private lateinit var currentFile: File

    private var mMediaProjection: MediaProjection? = null


    private fun initRecorder(context: Context) {
        isMediaRecorderError = false
        releaseMediaRecorderResource()

        val metrics = DisplayMetrics()
        val wm = context.getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getRealMetrics(metrics)

        val screenDensity = metrics.densityDpi
        val displayWidth = metrics.widthPixels
        val displayHeight = metrics.heightPixels

        mMediaRecorder =  when {
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> MediaRecorder(context)
            else -> MediaRecorder()
        }

        mMediaRecorder?.reset()
        mMediaRecorder?.apply {
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setVideoEncodingBitRate(BIT_RATE)
            setVideoFrameRate(FRAME_RATE)
            setVideoSize(displayWidth, displayHeight)

            createDirectory()
            fileName = SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(Date())
            filePath = filePathPrefix + StringBuilder(fileName)
                .append(FILE_FORMAT_VIDEO)
                .toString()
            currentFile = File(filePath)

            setOutputFile(currentFile)

            try {
                prepare()
            } catch (e: IllegalStateException) {
                Log.e(TAG, Log.getStackTraceString(e))
                isMediaRecorderError = true
            } catch (e: RuntimeException) {
                isMediaRecorderError = true
                Log.e(TAG, Log.getStackTraceString(e))
            } catch (e: IOException) {
                Log.e(TAG, Log.getStackTraceString(e))
                isMediaRecorderError = true
            } finally {
                clearResourceOnError()
            }
        }

        mMediaProjection?.let { mediaProjection ->
            mMediaRecorder?.let { mediaRecorder ->

                val surface: Surface = mediaRecorder.surface

                mediaProjection.createVirtualDisplay(
                    "MainActivity",
                    displayWidth,
                    displayHeight,
                    screenDensity,
                    VIRTUAL_DISPLAY_FLAG_PRESENTATION,
                    surface,
                    null,
                    null
                )
            }
        }


    }


    fun startScreenRecord(context: Context) {
        initRecorder(context)
        mMediaRecorder?.start()
    }

    fun stopScreenRecord() {
        if (mMediaRecorder == null) return

        mMediaRecorder?.apply {
            try {
                stop()
                reset()
            } catch (e: IllegalStateException) {
                isMediaRecorderError = true
                Log.e(TAG, Log.getStackTraceString(e))
            } catch (e: RuntimeException) {
                isMediaRecorderError = true
                Log.e(TAG, Log.getStackTraceString(e))
            } catch (e: IOException) {
                isMediaRecorderError = true
                Log.e(TAG, Log.getStackTraceString(e))
            } finally {
                clearResourceOnError()
            }
        }
    }

    private fun releaseResource() {
        mMediaRecorder?.release()
        mMediaRecorder = null
    }

    private fun createDirectory() {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment
                    .DIRECTORY_DOWNLOADS
            ).toString() + FILE_FOLDER
        )
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    private fun clearResourceOnError() {
        if (isMediaRecorderError) {
            releaseResource()
        }
    }

    fun releaseMediaRecorderResource() {
        mMediaRecorder?.pause()
        if(mMediaRecorder != null){
            releaseResource()
        }
    }

    fun getMediaProjectionObj(context: Context, resultCode: Int, data: Intent) {
        val mProjectionManager =
            context.getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data)
    }

}
