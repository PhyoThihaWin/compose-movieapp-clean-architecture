package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.feature.movie.MovieNavPage

/**
 * Created by P.T.H.W on 27/03/2024.
 */

const val homeNavPageNavigationRoute = "HOME"
fun NavGraphBuilder.homeNavPage() {
    composable(
        route = homeNavPageNavigationRoute,
    ) {
        HomeNavPage()
    }
}

fun NavController.navigateToHomeNavPage(navOptions: NavOptions? = null) =
    navigate(homeNavPageNavigationRoute, navOptions)