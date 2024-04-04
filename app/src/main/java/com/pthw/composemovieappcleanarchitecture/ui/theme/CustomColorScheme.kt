package com.pthw.composemovieappcleanarchitecture.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Immutable
data class CustomColorsPalette(
    val navigationBarColor: Color = Color.Unspecified,
)

val OnLightCustomColorsPalette = CustomColorsPalette(
    navigationBarColor = Color(color = 0xFFFFFFFF),
)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    navigationBarColor = Color(color = 0xFF17181D),
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }