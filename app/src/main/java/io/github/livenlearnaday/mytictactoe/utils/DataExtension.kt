package io.github.livenlearnaday.mytictactoe.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra(key, T::class.java)
    } else {
        this.getParcelableExtra(key)
    }
}
