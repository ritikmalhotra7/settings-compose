package com.orane.setting_compose.feat_recompose.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun ReComposeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val sideEffectKey = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(true) {
        while(true){
            delay(1000)
            sideEffectKey.value += 1
        }
    }
    Text(
        text = sideEffectKey.toString()
    )
}