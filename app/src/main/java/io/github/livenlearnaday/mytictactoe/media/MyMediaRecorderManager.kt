package io.github.livenlearnaday.mytictactoe.media

import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import android.util.Size
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


object MyMediaRecorderManager {

    private const val TAG = "MyMediaRecorderManager"
    private const val DISPLAY_WIDTH: Int = 640
    private const val DISPLAY_HEIGHT: Int = 360
    private const val bitRate = 500 * 1000
    private const val frameRate = 30
    const val FILE_FORMAT_VIDEO = ".webm"
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


    private fun initRecorder() {
        if (mMediaRecorder != null) {
            releaseResource()
        }
        isMediaRecorderError = false
        mMediaRecorder = MediaRecorder()
        mMediaRecorder?.reset()
        mMediaRecorder?.apply {
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.WEBM)
            setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            setVideoFrameRate(frameRate)
            setVideoEncoder(MediaRecorder.VideoEncoder.VP8)
            setVideoEncodingBitRate(bitRate)

            createDirectory()
            fileName = SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(Date())
            filePath = filePathPrefix + StringBuilder(fileName)
                .append(FILE_FORMAT_VIDEO)
                .toString()
            currentFile = File(filePath)

            setOutputFile(currentFile)

            try {
                prepare()
                start()
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
    }


    fun startScreenRecord() {
        initRecorder()
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

}
