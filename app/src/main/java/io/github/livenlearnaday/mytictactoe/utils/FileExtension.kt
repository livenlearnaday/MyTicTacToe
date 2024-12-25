package io.github.livenlearnaday.mytictactoe.utils

import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager.FILE_FORMAT_VIDEO
import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager.filePathPrefix
import java.io.File


fun String.checkIfFileExist(): Boolean {
    val filePath = this.getVideoFilePath
    val file = File(filePath).absoluteFile
    return file.exists()
}

val String.getVideoFilePath: String
    get() = filePathPrefix + StringBuilder(this)
        .append(FILE_FORMAT_VIDEO)
        .toString()
