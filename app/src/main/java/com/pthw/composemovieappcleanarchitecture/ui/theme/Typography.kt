package com.pthw.composemovieappcleanarchitecture.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.pthw.composemovieappcleanarchitecture.R

/**
 * Created by P.T.H.W on 24/01/2024.
 */

val poppinsFont = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_black, FontWeight.Black)
)

val Typography = Typography().defaultFontFamily(poppinsFont)

fun Typography.defaultFontFamily(fontFamily: FontFamily): Typography {
    return this.copy(
        displayLarge = this.displayLarge.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        displayMedium = this.displayMedium.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        displaySmall = this.displaySmall.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        headlineLarge = this.headlineLarge.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        headlineMedium = this.headlineMedium.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        headlineSmall = this.headlineSmall.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        titleLarge = this.titleLarge.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        titleMedium = this.titleMedium.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        titleSmall = this.titleSmall.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        bodyLarge = this.bodyLarge.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        bodyMedium = this.bodyMedium.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        bodySmall = this.bodySmall.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        labelLarge = this.labelLarge.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        labelMedium = this.labelMedium.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        labelSmall = this.labelSmall.copy(
            fontFamily = fontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
}
