package com.orane.setting_compose.feat_core.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.orane.setting_compose.R

val BitCountFontFamily = FontFamily(
    Font(R.font.bit_count_bold, weight = FontWeight.Bold),
    Font(R.font.bit_count_medium, weight = FontWeight.Medium),
    Font(R.font.bit_count_regular, weight = FontWeight.Normal),
    Font(R.font.bit_count_semi_bold, weight = FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = BitCountFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = BitCountFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = BitCountFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = BitCountFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BitCountFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = BitCountFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
)