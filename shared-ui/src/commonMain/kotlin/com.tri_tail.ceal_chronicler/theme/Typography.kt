package com.tri_tail.ceal_chronicler.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = darkTextColor,
        shadow = Shadow(
            color = brightTextColor,
            offset = Offset(1.0f, 1.0f)
        )
    ),

    h2 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontSize = 20.sp,
        color = darkTextColor
    ),

    h3 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontSize = 16.sp,
        color = darkTextColor
    ),

    h4 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontSize = 12.sp,
        color = darkTextColor
    )
)