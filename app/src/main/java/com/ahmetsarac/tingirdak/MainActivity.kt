package com.ahmetsarac.tingirdak

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ahmetsarac.tingirdak.composables.HomePage
import com.ahmetsarac.tingirdak.composables.GoToAppSettings
import com.ahmetsarac.tingirdak.composables.RequestStoragePermission
import com.ahmetsarac.tingirdak.ui.theme.TingirdakTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windows = this.window
        windows.statusBarColor = Color.argb(255, 0, 13, 107)
        setContent {
            TingirdakTheme {
                val storagePermissionState = rememberPermissionState(
                    permission = Manifest.permission.READ_EXTERNAL_STORAGE
                )

                when {
                    storagePermissionState.hasPermission -> {
                        HomePage()
                    }
                    storagePermissionState.hasBeenDeniedPermanently() -> {
                        GoToAppSettings()
                    }
                    else -> {
                        RequestStoragePermission(storagePermissionState)
                    }
                }
            }
        }
    }
}

/**
 * If the permission requested and we don't want to show the rationale that means permission denied permanently
 * On startup it will return false because to determine the permission the dialog has to shown at least once
 */
@ExperimentalPermissionsApi
fun PermissionState.hasBeenDeniedPermanently(): Boolean{
    return this.permissionRequested && !this.shouldShowRationale
}
