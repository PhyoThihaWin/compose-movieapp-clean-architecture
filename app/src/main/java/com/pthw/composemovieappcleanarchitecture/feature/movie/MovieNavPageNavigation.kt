package com.pthw.composemovieappcleanarchitecture.feature.movie

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 25/03/2024.
 */

const val movieNavPageNavigationRoute = "MOVIE"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieNavPage(
    sharedTransitionScope: SharedTransitionScope
) {
    composable(
        route = movieNavPageNavigationRoute,
    ) {
        MovieNavPage(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToMovieNavPage(navOptions: NavOptions? = null) =
    navigate(movieNavPageNavigationRoute, navOptions)


