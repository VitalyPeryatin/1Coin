package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
@Suppress("ConstructorParameterNaming")
data class CoinTypography(
    val h1: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 42.sp
    ),
    val h2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp
    ),
    val h3: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    val h4: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = Color.Black
    ),
    val h5: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    val h6: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    val body1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val body1_medium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    val body2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    val body2_medium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    val subtitle1: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ),
    val subtitle2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    val subtitle3: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val subtitle4: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    ),
    val subtitle5: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)
