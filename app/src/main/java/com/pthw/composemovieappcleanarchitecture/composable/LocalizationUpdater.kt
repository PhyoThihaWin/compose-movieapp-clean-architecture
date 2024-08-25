package com.pthw.composemovieappcleanarchitecture.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import timber.log.Timber
import java.util.*

/**
 * Created by P.T.H.W on 21/07/2024.
 */

@Composable
fun LocalizationUpdater(localeCode: String) {
    val locale = Locale(localeCode)
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
    Timber.i("Locale: ${configuration.locales[0]}")
}