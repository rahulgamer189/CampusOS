package com.example.campusos

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

// Common Dark Base
private val DarkBackground = Color(0xFF0F1115)
private val DarkSurface = Color(0xFF181B21)
private val DarkCard = Color(0xFF22252B)
private val DarkTextPrimary = Color(0xFFFFFFFF)
private val DarkTextSecondary = Color(0xFFA1A1AA)

// RED Palette
private val RedPrimary = Color(0xFFEF4444)
private val RedPrimaryDark = Color(0xFFDC2626)
private val RedAccent = Color(0xFFFF6B6B)
private val RedPale = Color(0xFFFEE2E2)

// BLUE Palette
private val BluePrimary = Color(0xFF3B82F6)
private val BluePrimaryDark = Color(0xFF2563EB)
private val BlueAccent = Color(0xFF38BDF8)
private val BluePale = Color(0xFFDBEAFE)

// PURPLE Palette
private val PurplePrimary = Color(0xFF8B5CF6)
private val PurplePrimaryDark = Color(0xFF7C3AED)
private val PurpleAccent = Color(0xFFC084FC)
private val PurplePale = Color(0xFFEDE9FE)

@Composable
fun CampusOSTheme(
    preferences: AppPreferences = AppPreferences(),
    content: @Composable () -> Unit
) {
    val isDark = preferences.isDarkMode || (preferences.theme != AppTheme.BLUE && isSystemInDarkTheme()) // Following prompt's specific palette which looks dark
    
    val colorScheme = when (preferences.theme) {
        AppTheme.RED -> if (isDark) {
            darkColorScheme(primary = RedPrimary, secondary = RedAccent, background = DarkBackground, surface = DarkSurface, onBackground = DarkTextPrimary, onSurface = DarkTextPrimary)
        } else {
            lightColorScheme(primary = RedPrimary, secondary = RedPrimaryDark, background = RedPale, surface = Color.White)
        }
        AppTheme.BLUE -> if (isDark) {
            darkColorScheme(primary = BluePrimary, secondary = BlueAccent, background = DarkBackground, surface = DarkSurface, onBackground = DarkTextPrimary, onSurface = DarkTextPrimary)
        } else {
            lightColorScheme(primary = BluePrimary, secondary = BluePrimaryDark, background = BluePale, surface = Color.White)
        }
        AppTheme.PURPLE -> if (isDark) {
            darkColorScheme(primary = PurplePrimary, secondary = PurpleAccent, background = DarkBackground, surface = DarkSurface, onBackground = DarkTextPrimary, onSurface = DarkTextPrimary)
        } else {
            lightColorScheme(primary = PurplePrimary, secondary = PurplePrimaryDark, background = PurplePale, surface = Color.White)
        }
    }

    val typography = Typography(
        bodyLarge = TextStyle(fontSize = (16 * preferences.fontSizeMultiplier).sp),
        titleLarge = TextStyle(fontSize = (22 * preferences.fontSizeMultiplier).sp),
        headlineMedium = TextStyle(fontSize = (28 * preferences.fontSizeMultiplier).sp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
