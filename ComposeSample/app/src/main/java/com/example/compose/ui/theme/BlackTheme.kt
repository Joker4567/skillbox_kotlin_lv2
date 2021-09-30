package com.example.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.compose.ui.theme.attr.*

private val BlackLightColorPalette = lightColors(
    primary = Black,
    primaryVariant = Black,
    secondary = DarkGray,
    secondaryVariant = Black,
    surface = DarkGray
)

private val BlackDarkColorPalette = darkColors(
    primary = Black,
    primaryVariant = Black,
    secondary = Black,
    secondaryVariant = Black,
    surface = Black
)

@Composable
fun BlackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    //TODO для соответствия макета применена only темная верстка
    ComposeSampleTheme(
        lightColorPalette = BlackDarkColorPalette,
        darkColorPalette = BlackDarkColorPalette,
        darkTheme = darkTheme,
        content = content
    )
}
