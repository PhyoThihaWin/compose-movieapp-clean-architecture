package com.pthw.composemovieappcleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import com.pthw.composemovieappcleanarchitecture.composable.LocalizationUpdater
import com.pthw.composemovieappcleanarchitecture.navigation.MainPage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.domain.general.AppThemeMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeMovieAppCleanArchitectureTheme(
                localization = viewModel.currentLanguage,
                darkTheme = isDarkMode(themeCode = viewModel.appThemeMode.value)
            ) {
                MainPage(
                    windowSizeClass = calculateWindowSizeClass(this),
                )
            }
        }
    }
}

@Composable
fun isDarkMode(themeCode: String) = when (themeCode) {
    AppThemeMode.LIGHT_MODE -> false
    AppThemeMode.DARK_MODE -> true
    else -> isSystemInDarkTheme()
}