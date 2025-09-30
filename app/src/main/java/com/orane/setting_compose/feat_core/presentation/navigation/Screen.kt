package com.orane.setting_compose.feat_core.presentation.navigation

sealed class Screen(val route:String){
    data object SplashScreen: Screen("splashScreen")
    data object HomeScreen: Screen("homeScreen")
    data object TicTacToeScreen:Screen("ticTacToeScreen")
    data object IOTScreen:Screen("iotScreen")
    data object ReComposeScreen:Screen("reComposeScreen")
}