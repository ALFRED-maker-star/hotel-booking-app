package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = NavyDark,
    primaryContainer = NavyLight,
    onPrimaryContainer = GoldContainer,
    secondary = GoldLight,
    onSecondary = NavyDark,
    background = NavyDark,
    onBackground = IvoryBackground,
    surface = NavyPrimary,
    onSurface = IvoryBackground,
    surfaceVariant = NavyLight,
    onSurfaceVariant = IvoryMuted,
    outline = SlateMuted
)

private val LightColorScheme = lightColorScheme(
    primary = NavyPrimary,
    onPrimary = Color.White,
    primaryContainer = IvoryMuted,
    onPrimaryContainer = NavyPrimary,
    secondary = GoldAccent,
    onSecondary = NavyDark,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = GoldDark,
    tertiary = GoldDark,
    background = IvoryBackground,
    onBackground = SlateText,
    surface = IvoryCard,
    onSurface = SlateText,
    surfaceVariant = IvoryMuted,
    onSurfaceVariant = SlateMuted,
    outline = SlateBorder
)

@Composable
fun StayEaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = StayEaseTypography,
        content = content
    )
}
