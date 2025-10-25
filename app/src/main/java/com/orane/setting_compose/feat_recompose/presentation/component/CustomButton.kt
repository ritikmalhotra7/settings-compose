package com.orane.setting_compose.feat_recompose.presentation.component

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(label:String, onClick:()->Unit, modifier: Modifier = Modifier) {
    Log.d("taget-outside","custom-button-recomposed-outside of side effect")

    SideEffect {
        Log.d("taget-inside","custom-button-recomposed-inside of side effect")
    }

    LaunchedEffect(true) {
        Log.d("taget-inside","custom-button-recomposed-inside of launched effect")
    }
    Button(
        onClick = onClick,
        modifier = modifier
    ){
        val s = remember{
            mutableStateOf(
                0
            )
        }
        Log.d("taget-outside","custom-button-recomposed-outside of side effect 2")

        SideEffect {
            Log.d("taget-inside","custom-button-recomposed-inside of side effect 2")
        }

        LaunchedEffect(label) {
            Log.d("taget-inside","custom-button-recomposed-inside of launched effect 2")
            s.value += 1
        }
        Text(text = s.value.toString())
    }
    Text(text = label)
}