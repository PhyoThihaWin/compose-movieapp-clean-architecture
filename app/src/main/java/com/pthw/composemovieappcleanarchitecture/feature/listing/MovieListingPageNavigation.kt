package com.pthw.composemovieappcleanarchitecture.feature.listing

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 04/04/2024.
 */

const val movieListingPageNavigationRoute = "movie-listing"
fun NavGraphBuilder.movieListingPage(
    navController: NavController
) {
    composable(
        route = movieListingPageNavigationRoute,
    ) {
        MovieListingPage(navController = navController)
    }
}

fun NavController.navigateToMovieListingPage(navOptions: NavOptions? = null) =
    navigate(movieListingPageNavigationRoute, navOptions)