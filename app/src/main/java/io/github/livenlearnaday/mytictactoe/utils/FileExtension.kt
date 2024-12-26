package io.github.livenlearnaday.mytictactoe.utils

import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager.FILE_FORMAT_VIDEO
import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager.filePathPrefix
import java.io.File


fun String.checkIfFileExistByFileName(): Boolean {
    val filePath = this.getVideoFilePathFromFileName
    val file = File(filePath).absoluteFile
    return file.exists()
}

val String.getVideoFilePathFromFileName: String
    get() = filePathPrefix + StringBuilder(this)
        .append(FILE_FORMAT_VIDEO)
        .toString()

fun String.deleteFileByFileName() {
    val filePath = this.getVideoFilePathFromFileName
    val file = File(filePath).absoluteFile
    if (file.exists()) {
        file.delete()
    }
}