package com.ahmetsarac.tingirdak.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@ExperimentalPermissionsApi
@Composable
fun RequestStoragePermission(storagePermissionState: PermissionState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("To list your music files the app needs the permission.")
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            storagePermissionState.launchPermissionRequest()
        }) {
            Text(text = "Request Permission")
        }
    }
}

