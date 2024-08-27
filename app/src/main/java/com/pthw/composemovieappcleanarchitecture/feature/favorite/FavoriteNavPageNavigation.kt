package com.pthw.composemovieappcleanarchitecture.feature.favorite

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.navigation.NavRoute

/**
 * Created by P.T.H.W on 25/03/2024.
 */

fun NavGraphBuilder.favoriteNavPage() {
    composable<NavRoute.FavoritePage> {
        FavoriteNavPage()
    }
}

fun NavController.navigateToFavoriteNavPage(navOptions: NavOptions? = null) =
    navigate(NavRoute.FavoritePage, navOptions)
