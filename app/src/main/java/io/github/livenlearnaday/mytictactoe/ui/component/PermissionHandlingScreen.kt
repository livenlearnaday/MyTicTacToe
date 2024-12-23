package io.github.livenlearnaday.mytictactoe.ui.component

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.MultiplePermissionsState
import io.github.livenlearnaday.mytictactoe.R

@Composable
fun PermissionHandlingScreen(navController: NavHostController, permissionsStates: MultiplePermissionsState) {

    val context = LocalContext.current
 // Check if the permission request has been processed
    var hasRequestedPermission by rememberSaveable {  mutableStateOf(false) }
    var permissionRequestCompleted by rememberSaveable { mutableStateOf(false) }



    LaunchedEffect(permissionsStates.revokedPermissions) {
        // Check if the permission state has changed after the request
        if (hasRequestedPermission) {
            permissionRequestCompleted = true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        when {
            permissionsStates.allPermissionsGranted -> {
                // If all permissions are granted, show success message
                Text("All permissions granted. Please click the button or backpress to proceed to the app.")
                Button(onClick = { navController.popBackStack() }, Modifier.padding(top = 16.dp)) {
                    Text("Go Back")
                }
                navController.popBackStack()
            }
            permissionsStates.shouldShowRationale -> {
                // Show rationale if needed and give an option to request permissions
                Text("Permissions are required to use the app.")
                Button(onClick = {
                    permissionsStates.launchMultiplePermissionRequest()
                }) {
                    Text("Request Permissions")
                }
            }

            else -> {
                if (permissionRequestCompleted) {
                    // Show permission denied message only after interaction
                    Text("Permissions denied. Please enable them in the app settings to proceed.")
                    Button(onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }) {
                        Text("Open App Settings")
                    }

                } else {
                    // Display the initial request button
                    Text("Permissions are required to use the app.")
                    Button(onClick = {
                        permissionsStates.launchMultiplePermissionRequest()
                        hasRequestedPermission = true
                    }) {
                        Text(stringResource(R.string.grant_app_permissions))
                    }
                }

            }
        }
    }
}
