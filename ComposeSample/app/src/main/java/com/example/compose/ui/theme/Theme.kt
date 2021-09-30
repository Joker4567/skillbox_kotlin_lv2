package com.example.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.attr.*

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun ComposeSampleTheme(
    lightColorPalette: Colors = LightColorPalette,
    darkColorPalette: Colors = DarkColorPalette,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }
    CompositionLocalProvider(
        LocalPaddings provides Paddings(),
        LocalElevations provides Elevations(card = 8.dp)
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object JetpackComposeMaterialTheme {
    /**
     * Proxy to [MaterialTheme]
     */
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    /**
     * Proxy to [MaterialTheme]
     */
    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    /**
     * Proxy to [MaterialTheme]
     */
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    /**
     * Retrieves the current [Paddings].
     */
    val paddings: Paddings
        @Composable
        get() = LocalPaddings.current

    /**
     * Retrieves the current [Paddings].
     */
    val elevations: Elevations
        @Composable
        get() = LocalElevations.current

}
