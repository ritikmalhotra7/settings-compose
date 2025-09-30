package com.orane.setting_compose.feat_core.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.orane.setting_compose.feat_core.presentation.components.HomeScreenButton
import com.orane.setting_compose.feat_core.presentation.navigation.Screen

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val itemList = listOf(Pair("Tic Tac Toe", Screen.TicTacToeScreen),Pair("IOT",Screen.IOTScreen), Pair("ReCompose",Screen.ReComposeScreen))
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = itemList.size, key = { it }) { position ->
            HomeScreenButton(
                text = itemList[position].first,
                isEnabled = true,
                onClick = { navController.navigate(itemList[position].second.route) }
            )
        }
    }
}