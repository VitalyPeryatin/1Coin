package com.finance_tracker.finance_tracker.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class CoinColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val content: Color,
    val selectedContentColor: Color,
    val unSelectedContentColor: Color,
    val textColor: Color,
    val iconColor: Color,
    val increaseColor: Color,
    val decreaseColor: Color
)

val LocalCustomColors = staticCompositionLocalOf {
    CoinColors(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        content = Color.Unspecified,
        selectedContentColor = Color.Unspecified,
        unSelectedContentColor = Color.Unspecified,
        textColor = Color.Unspecified,
        iconColor = Color.Unspecified,
        increaseColor = Color.Unspecified,
        decreaseColor = Color.Unspecified,
    )
}
val LocalCoinTypography = staticCompositionLocalOf {
    CoinTypography(
        body = TextStyle.Default,
        title = TextStyle.Default
    )
}
val LocalCoinElevation = staticCompositionLocalOf {
    CoinElevation(
        default = Dp.Unspecified,
        pressed = Dp.Unspecified
    )
}

private val DarkColorPalette = CoinColors(
    primary = AppColors.Purple200,
    primaryVariant = AppColors.Purple700,
    secondary = AppColors.Teal200,
    background = AppColors.Purple500,
    content = AppColors.White,
    selectedContentColor = AppColors.White,
    unSelectedContentColor = AppColors.White,
    textColor = AppColors.Black,
    iconColor = AppColors.Black,
    increaseColor = Color.Unspecified,
    decreaseColor = Color.Unspecified,
)
private val LightColorPalette = CoinColors(
    primary = AppColors.Purple500,
    primaryVariant = AppColors.Purple700,
    secondary = AppColors.Teal200,
    background = AppColors.Purple500,
    content = AppColors.White,
    selectedContentColor = AppColors.White,
    unSelectedContentColor = AppColors.White,
    textColor = AppColors.Black,
    iconColor = AppColors.Black,
    increaseColor = AppColors.green,
    decreaseColor = AppColors.Black,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val coinColors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val coinTypography = CoinTypography(
        body = TextStyle(fontSize = 16.sp),
        title = TextStyle(fontSize = 32.sp)
    )
    val coinElevation = CoinElevation(
        default = 4.dp,
        pressed = 8.dp
    )

    CompositionLocalProvider(
        LocalCustomColors provides coinColors,
        LocalCoinTypography provides coinTypography,
        LocalCoinElevation provides coinElevation,
        content = content
    )

}

object CoinTheme {
    val color: CoinColors
        @Composable
        get() = LocalCustomColors.current
    val typography: CoinTypography
        @Composable
        get() = LocalCoinTypography.current
    val elevation: CoinElevation
        @Composable
        get() = LocalCoinElevation.current
}