package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 12/04/2024.
 */

const val movieDetailPageNavigationRoute = "movie-detail/{movieId}"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieDetailPage(
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route = movieDetailPageNavigationRoute,
    ) {
        MovieDetailPage(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToMovieDetailPage(movieId: Int, navOptions: NavOptions? = null) =
    navigate("movie-detail/$movieId", navOptions)