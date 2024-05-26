package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.feature.movie.MovieNavPage

/**
 * Created by P.T.H.W on 27/03/2024.
 */

const val homeNavPageNavigationRoute = "HOME"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeNavPage(
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route = homeNavPageNavigationRoute,
    ) {
        HomeNavPage(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToHomeNavPage(navOptions: NavOptions? = null) =
    navigate(homeNavPageNavigationRoute, navOptions)