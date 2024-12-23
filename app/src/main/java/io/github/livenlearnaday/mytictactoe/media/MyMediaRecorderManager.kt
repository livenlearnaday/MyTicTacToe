package io.github.livenlearnaday.mytictactoe.media

import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


object MyMediaRecorderManager {

    private const val DISPLAY_WIDTH: Int = 720
    private const val DISPLAY_HEIGHT: Int = 1280
    const val FILE_FORMAT_VIDEO = ".mp4"
    private const val FILE_FOLDER = "/mytictactoe"
    private const val SEPARATOR = "/"


    private var mMediaRecorder: MediaRecorder? = null

    var fileName: String = ""

    var filePath: String = ""

    var filePathPrefix: String = ""

    var isMediaRecorderError: Boolean = false

    var isAbortGame: Boolean = false

    private lateinit var currentFile: File


    private fun initRecorder() {
        if (mMediaRecorder != null) {
            releaseResource()
        }
        isMediaRecorderError = false
        mMediaRecorder = MediaRecorder()
        mMediaRecorder?.reset()
        mMediaRecorder?.apply {
            fileName = SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(Date())
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setVideoEncodingBitRate(512 * 1000)
            createDirectory()
            filePathPrefix = Environment.getExternalStoragePublicDirectory(
                Environment
                    .DIRECTORY_DOWNLOADS
            )
                .toString() + StringBuilder(FILE_FOLDER)
                .append(SEPARATOR)
            filePath = filePathPrefix + StringBuilder(fileName)
                .append(FILE_FORMAT_VIDEO)
                .toString()
            currentFile = File(filePath)
            setOutputFile(currentFile.absolutePath)
            setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            setVideoFrameRate(30)
            setOrientationHint(0)
        }
        try {
            mMediaRecorder?.prepare()
        } catch (e: IOException) {
            Log.d("log", "screen record start error")
            isMediaRecorderError = true
            Log.e("MyMediaRecorderManager", "e: $e, e.messsage: ${e.message}")
            currentFile.delete()
            e.printStackTrace()
        } finally {
            releaseResource()
        }
    }


    fun startScreenRecord() {
        if (mMediaRecorder == null) {
            initRecorder()
            mMediaRecorder?.start()
        }

    }

    fun stopScreenRecord() {
        try {
            mMediaRecorder?.stop()
            mMediaRecorder?.reset()
            Log.d("log", "screen record stopped")
            if(isAbortGame) currentFile.delete()
        } catch (e: RuntimeException) {
            Log.d("log", "screen record stop error")
            isMediaRecorderError = true
            Log.e(
                "MyMediaRecorderManager",
                "e: $e, e.cause: ${e.cause},  e.stackTrace: ${e.stackTrace}"
            )
            currentFile.delete()
            e.printStackTrace()
        } finally {
            releaseResource()
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


}
