package com.orane.setting_compose.feat_core.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.orane.setting_compose.feat_core.domain.controllers.SnackBarController
import com.orane.setting_compose.feat_core.presentation.navigation.NavGraph
import com.orane.setting_compose.feat_core.presentation.ui.theme.SettingComposeTheme
import com.orane.setting_compose.feat_core.utils.ObserveAsEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var snackBarController: SnackBarController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainNavController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            SettingComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ObserveAsEvent(
                        flow = snackBarController.event,
                        keys = arrayOf(snackBarHostState)
                    ) { event ->
                        scope.launch {
                            snackBarHostState.currentSnackbarData?.dismiss()
                            val result = snackBarHostState.showSnackbar(
                                message = event.message,
                                actionLabel = event.action?.label,
                                duration = event.action?.let { SnackbarDuration.Indefinite }
                                    ?: SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                event.action?.action?.invoke()
                            }
                        }
                    }
                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                    ) { padding ->
                        NavGraph(
                            navController = mainNavController,
                            modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }
}