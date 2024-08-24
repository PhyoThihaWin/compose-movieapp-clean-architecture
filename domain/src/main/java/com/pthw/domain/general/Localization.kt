package com.pthw.domain.general

import androidx.annotation.StringRes
import java.io.Serializable

/**
 * Created by P.T.H.W on 21/07/2024.
 */

data class Localization(
    @StringRes val title: Int,
    val localeCode: String
) : Serializable {
    companion object {
        const val ENGLISH = "en"
        const val MYANMAR = "my"
    }
}

