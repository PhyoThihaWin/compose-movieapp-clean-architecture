package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 12/04/2024.
 */

const val movieDetailNavPageNavigationRoute = "movie-detail"
fun NavGraphBuilder.movieDetailPage(
    navController: NavController
) {
    composable(
        route = movieDetailNavPageNavigationRoute,
    ) {
        MovieDetailPage(navController = navController)
    }
}

fun NavController.navigateToMovieDetailPage(navOptions: NavOptions? = null) =
    navigate(movieDetailNavPageNavigationRoute, navOptions)