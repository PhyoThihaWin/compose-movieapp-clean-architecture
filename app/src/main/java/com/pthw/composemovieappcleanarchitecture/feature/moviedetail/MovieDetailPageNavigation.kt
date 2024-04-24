package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 12/04/2024.
 */

const val movieDetailPageNavigationRoute = "movie-detail/{movieId}"
fun NavGraphBuilder.movieDetailPage(
    navController: NavController
) {
    composable(
        route = movieDetailPageNavigationRoute,
    ) {
        MovieDetailPage(navController = navController)
    }
}

fun NavController.navigateToMovieDetailPage(movieId: Int, navOptions: NavOptions? = null) =
    navigate("movie-detail/$movieId", navOptions)