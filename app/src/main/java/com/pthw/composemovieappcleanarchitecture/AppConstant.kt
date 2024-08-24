package com.pthw.composemovieappcleanarchitecture

import com.pthw.domain.general.Localization

/**
 * Created by P.T.H.W on 22/08/2024.
 */

object AppConstant {
    const val NowPlayingMoviesKey = "nowPlaying-image-%s"
    const val ComingSoonMoviesKey = "comingSoon-image-%s"
    const val PromotionMoviesKey = "promotion-image-%s"
    const val ListingMoviesKey = "listing-image-%s"

    val languageList = listOf(
        Localization(R.string.locale_english, Localization.ENGLISH),
        Localization(R.string.locale_myanmar, Localization.MYANMAR)
    )
}