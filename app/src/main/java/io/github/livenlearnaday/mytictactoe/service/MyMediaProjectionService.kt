package io.github.livenlearnaday.mytictactoe.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.github.livenlearnaday.mytictactoe.MainActivity
import io.github.livenlearnaday.mytictactoe.MainActivity.Companion.ACTION_MEDIA_PROJECTION_STARTED
import io.github.livenlearnaday.mytictactoe.utils.getParcelable


class MyMediaProjectionService: Service() {

    companion object {
        const val SERVICE_ID: Int = 1117
        const val NOTIFICATION_CHANNEL_ID = "io.github.livenlearnaday.mytictactoe.ScreenCaptureService"
        const val NOTIFICATION_CHANNEL_NAME = "ScreenCaptureService"
    }

    private var notification: Notification? = null
    private var notificationManager: NotificationManager? =null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_NONE
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
            notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID).build()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            notification?.let {
                startForeground(
                    SERVICE_ID,
                    it,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
                )
            }

        }

        val resultCode = intent.getIntExtra("resultCode", -1)
        val data = intent.getParcelable<Intent>("data")

        val broadcastIntent = Intent(
            this,
            MainActivity.MyBroadcastReceiver::class.java
        )
        broadcastIntent.setAction(ACTION_MEDIA_PROJECTION_STARTED)
        broadcastIntent.putExtra("resultCode", resultCode)
        broadcastIntent.putExtra("data", data)

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)

        notification?.let {
            stopForeground(STOP_FOREGROUND_REMOVE)
        }

        return START_STICKY
    }


}