package com.pthw.composemovieappcleanarchitecture.feature.movie

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.navigation.NavRoute

/**
 * Created by P.T.H.W on 25/03/2024.
 */


@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieNavPage(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<NavRoute.MoviePage> {
        MovieNavPage(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToMovieNavPage(navOptions: NavOptions? = null) =
    navigate(NavRoute.MoviePage, navOptions)


