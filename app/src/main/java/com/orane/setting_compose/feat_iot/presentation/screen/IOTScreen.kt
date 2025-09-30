package com.orane.setting_compose.feat_iot.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.orane.setting_compose.feat_core.presentation.navigation.Screen

@Composable
fun IOTScreen(navController:NavHostController, modifier: Modifier = Modifier) {
    val toggleState = remember { mutableStateOf(false) }
    Box(modifier = modifier){
        Button(onClick ={
            toggleState.value = !toggleState.value
        }) {
            Text(text = "Turn IOT " + if(toggleState.value) "ON" else "OFF")
        }
    }
}