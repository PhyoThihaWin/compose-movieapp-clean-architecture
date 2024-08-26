package com.pthw.domain.general

import androidx.annotation.StringRes
import java.io.Serializable

/**
 * Created by P.T.H.W on 23/07/2024.
 */
data class AppThemeMode(
    @StringRes val title: Int,
    val themeCode: String
) : Serializable {
    companion object {
        const val LIGHT_MODE = "light_mode"
        const val DARK_MODE = "dark_mode"
        const val SYSTEM_DEFAULT = "system_default"
    }
}
