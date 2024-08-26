package com.pthw.composemovieappcleanarchitecture.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Immutable
data class CustomColorsScheme(
    val searchBoxColor: Color = Color.Unspecified,
) {
    companion object {
        val OnLight = CustomColorsScheme(
            searchBoxColor = Color(color = 0xFFEEEEEE),
        )

        val OnDark = CustomColorsScheme(
            searchBoxColor = Color.DarkGray,
        )
    }
}

