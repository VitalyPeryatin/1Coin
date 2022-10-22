package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.asSp
import com.finance_tracker.finance_tracker.core.common.getContext

@Immutable
data class CoinColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val secondaryBackground: Color,
    val dividers: Color,
    val content: Color,
    val accentGreen: Color
)

val LocalCoinColors = staticCompositionLocalOf {
    CoinColors(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        secondaryBackground = Color.Unspecified,
        dividers = Color.Unspecified,
        content = Color.Unspecified,
        accentGreen = Color.Unspecified
    )
}
val LocalCoinTypography = staticCompositionLocalOf {
    CoinTypography()
}
val LocalCoinElevation = staticCompositionLocalOf {
    CoinElevation(
        default = 4.dp,
        pressed = 8.dp
    )
}

// Ripple

@Immutable
private object CoinRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(LocalContentColor.current, lightTheme = true)

    @Composable
    override fun rippleAlpha() = DefaultRippleAlpha
}

private val DefaultRippleAlpha = RippleAlpha(
    pressedAlpha = 0.12f,
    focusedAlpha = 0.12f,
    draggedAlpha = 0.16f,
    hoveredAlpha = 0.08f
)

private val DarkColorPalette = CoinColors(
    primary = Color(0xFF009BFF),
    primaryVariant = Color(0xFFFFFFFF),
    secondary = Color.Black.copy(alpha = 0.4f),
    background = Color.White,
    secondaryBackground = Color(0xFFF7F7F7),
    dividers = Color(0xFFE6E6E6),
    content = Color.Black,
    accentGreen = Color(0xFF00BC2D)
)
private val LightColorPalette = CoinColors(
    primary = Color(0xFF009BFF),
    primaryVariant = Color(0xFFFFFFFF),
    secondary = Color.Black.copy(alpha = 0.4f),
    background = Color.White,
    secondaryBackground = Color(0xFFF7F7F7),
    dividers = Color(0xFFE6E6E6),
    content = Color.Black,
    accentGreen = Color(0xFF00BC2D)
)

@Composable
fun TextStyle.staticTextSize(isStaticContentSize: Boolean = true): TextStyle {
    return if (isStaticContentSize) copy(fontSize = fontSize.value.dp.asSp()) else this
}

@Composable
fun CoinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val coinColors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalContext provides getContext(),
        LocalCoinColors provides coinColors,
        LocalCoinTypography provides CoinTheme.typography,
        LocalCoinElevation provides CoinTheme.elevation,
        LocalContentColor provides CoinTheme.color.content,
        LocalIndication provides rememberRipple(),
        LocalRippleTheme provides CoinRippleTheme,
        content = content
    )

}

object CoinTheme {
    val color: CoinColors
        @Composable
        get() = LocalCoinColors.current
    val typography: CoinTypography
        @Composable
        get() = LocalCoinTypography.current
    val elevation: CoinElevation
        @Composable
        get() = LocalCoinElevation.current
}