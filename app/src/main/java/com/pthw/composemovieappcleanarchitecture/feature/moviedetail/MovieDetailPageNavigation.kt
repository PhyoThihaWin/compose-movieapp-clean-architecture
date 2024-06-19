package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.domain.home.model.MovieVo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Created by P.T.H.W on 12/04/2024.
 */

const val movieDetailPageNavigationRoute = "movie-detail/{movieId}/{backdropPath}"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieDetailPage(
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<MovieVo> {
        MovieDetailPage(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToMovieDetailPage(
    movieVo: MovieVo,
    navOptions: NavOptions? = null
) {
    navigate(movieVo)
}