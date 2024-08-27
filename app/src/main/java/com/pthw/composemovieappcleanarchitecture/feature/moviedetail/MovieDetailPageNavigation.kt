package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.navigation.NavRoute
import com.pthw.domain.home.model.MovieVo

/**
 * Created by P.T.H.W on 12/04/2024.
 */

const val movieDetailPageNavigationRoute = "movie-detail/{movieId}/{backdropPath}"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieDetailPage(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<NavRoute.MovieDetail> {
        MovieDetailPage(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToMovieDetailPage(
    sharedKey: String,
    movie: MovieVo,
    navOptions: NavOptions? = null
) {
    navigate(
        NavRoute.MovieDetail(
            sharedKey = sharedKey,
            id = movie.id,
            backdropPath = movie.backdropPath
        )
    )
}