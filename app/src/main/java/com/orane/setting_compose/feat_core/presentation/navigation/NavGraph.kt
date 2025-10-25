package com.orane.setting_compose.feat_core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.orane.setting_compose.feat_core.presentation.screens.HomeScreen
import com.orane.setting_compose.feat_core.presentation.screens.SplashScreen
import com.orane.setting_compose.feat_recompose.presentation.screen.ReComposeScreen
import com.orane.setting_compose.feat_scanner.presentation.screens.ScannerScreen
import com.orane.setting_compose.feat_tic_tac.presentation.screens.TicTacToeScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) { SplashScreen(navController) }
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.TicTacToeScreen.route) { TicTacToeScreen(navController) }
        composable(Screen.ReComposeScreen.route) { ReComposeScreen(navController) }
        composable(Screen.ScannerScreen.route) { ScannerScreen(navController) }
    }
}