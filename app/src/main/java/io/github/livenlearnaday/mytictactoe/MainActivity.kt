package io.github.livenlearnaday.mytictactoe

import MyTicTacToeTheme
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.github.livenlearnaday.mytictactoe.media.MyMediaRecorderManager
import io.github.livenlearnaday.mytictactoe.navigation.GameScreenRoute
import io.github.livenlearnaday.mytictactoe.service.MyMediaProjectionService
import io.github.livenlearnaday.mytictactoe.ui.component.PermissionHandlingScreen
import io.github.livenlearnaday.mytictactoe.utils.getParcelable


class MainActivity : ComponentActivity() {
    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName

        const val ACTION_MEDIA_PROJECTION_STARTED: String =
            "io.github.livenlearnaday.mytictactoe.ACTION_MEDIA_PROJECTION_STARTED"
    }

    private var isReceiverRegistered = false

    private var mediaProjectionManager: MediaProjectionManager? = null

    private var mMediaProjection: MediaProjection? = null

    private var startMediaProjectionActivity: ActivityResultLauncher<Intent>? = null

    class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ACTION_MEDIA_PROJECTION_STARTED == intent.action) {
                val resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED)
                val data = intent.getParcelable<Intent>("data")
                data?.let {
                    MyMediaRecorderManager.getMediaProjectionObj(context, resultCode, data)
                }
            }
        }

    }

    private val receiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                MyTicTacToeTheme {
                    val navController = rememberNavController()


                    val permissionStates = rememberMultiplePermissionsState(
                        permissions = listOf(
                            WRITE_EXTERNAL_STORAGE
                        )
                    )

                    if (permissionStates.allPermissionsGranted) {
                        requestScreenCapturePermission()
                        Navigator(screen = GameScreenRoute)
                    } else {
                        PermissionHandlingScreen(navController = navController, permissionStates)
                    }
                }
            }
        }
    override fun onStart() {
        super.onStart()
        mediaProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager


        startMediaProjectionActivity =
            registerForActivityResult<Intent, ActivityResult>(
                ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                val resultCode = result.resultCode
                if (result.resultCode == RESULT_OK) {
                    val intentData = result.data
                    intentData?.let { data ->

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                            val projectionManager =
                                getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
                            mMediaProjection =
                                projectionManager.getMediaProjection(resultCode, data)
                            mMediaProjection?.let {
                                MyMediaRecorderManager.getMediaProjectionObj(this, resultCode, data)
                            }
                        } else {
                            try {
                                val serviceIntent =
                                    Intent(
                                        this,
                                        MyMediaProjectionService::class.java
                                    )
                                serviceIntent.putExtra("resultCode", resultCode)
                                serviceIntent.putExtra("data", data)

                                ContextCompat.startForegroundService(this, serviceIntent)
                            } catch (e: RuntimeException) {
                                Log.e(
                                    TAG,
                                    "Error while trying to get the MediaProjection instance: " + e.message
                                )
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Screen sharing permission denied. Game plays will not be recorded.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        if (!isReceiverRegistered) {
            val filter = IntentFilter(ACTION_MEDIA_PROJECTION_STARTED)
            filter.addCategory(Intent.CATEGORY_DEFAULT)

            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter)
            isReceiverRegistered = true
        }

    }

    override fun onStop() {
        super.onStop()
        if (isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
            isReceiverRegistered = false
        }
        MyMediaRecorderManager.stopScreenRecord()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
        isReceiverRegistered = false
        super.onDestroy()
    }

    private fun requestScreenCapturePermission() {
        if (startMediaProjectionActivity != null) {
            mediaProjectionManager =
                getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

            val captureIntent = mediaProjectionManager!!.createScreenCaptureIntent()
            startMediaProjectionActivity!!.launch(captureIntent)
        }
    }

}