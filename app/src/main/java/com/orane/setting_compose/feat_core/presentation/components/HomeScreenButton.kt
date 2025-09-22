package com.orane.setting_compose.feat_core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenButton(text:String, modifier: Modifier = Modifier, isEnabled :Boolean, onClick:()->Unit) {
    Button(
        modifier = modifier.padding(8.dp),
        onClick = onClick,
        enabled = isEnabled,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}