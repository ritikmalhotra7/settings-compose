package com.orane.setting_compose.feat_scanner.presentation.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.orane.setting_compose.feat_core.presentation.navigation.Screen

@Composable
fun ScannerScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result -> Log.d("taget", "scanned code: ${result.contents}") }
    )
    LaunchedEffect(true) {
        scanLauncher.launch(ScanOptions())
    }
}