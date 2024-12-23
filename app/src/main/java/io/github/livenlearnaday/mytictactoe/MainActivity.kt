package io.github.livenlearnaday.mytictactoe

import MyTicTacToeTheme
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.github.livenlearnaday.mytictactoe.navigation.GameScreenRoute
import io.github.livenlearnaday.mytictactoe.ui.component.PermissionHandlingScreen


class MainActivity : ComponentActivity() {

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
                        Navigator(screen = GameScreenRoute)
                    } else {
                        PermissionHandlingScreen(navController = navController, permissionStates)
                    }
                }
            }
        }

}