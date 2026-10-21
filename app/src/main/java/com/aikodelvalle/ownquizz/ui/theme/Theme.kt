package com.aikodelvalle.ownquizz.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = VioletaPrimario,
    onPrimary = Color.White,
    primaryContainer = VioletaContenedor,
    onPrimaryContainer = VioletaSobreContenedor,
    secondaryContainer = GrisContenedor,
    onSecondaryContainer = TextoPrincipal,
    background = Color.White,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal,
    surfaceVariant = FondoTarjetaPregunta,
    onSurfaceVariant = TextoPrincipal,
    outline = BordeNeutro,
    error = ErrorRojo
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB9A9FF),
    onPrimary = VioletaSobreContenedor,
    primaryContainer = Color(0xFF4330A8),
    onPrimaryContainer = VioletaContenedor,
    secondaryContainer = Color(0xFF3A3748),
    onSecondaryContainer = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF2B2930),
    onSurfaceVariant = Color(0xFFE6E1E5)
)

@Composable
fun OwnQuizzTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}