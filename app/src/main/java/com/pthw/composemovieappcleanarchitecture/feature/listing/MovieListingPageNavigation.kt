package com.pthw.composemovieappcleanarchitecture.feature.listing

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 04/04/2024.
 */

const val movieListingPageNavigationRoute = "movie-listing/{movieType}"

class MovieListingPageNavigation {
    companion object {
        const val NOW_PLAYING = "NowPlayingMovies"
        const val COMING_SOON = "ComingSoonMovies"
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieListingPage(
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route = movieListingPageNavigationRoute,
    ) {
        MovieListingPage(sharedTransitionScope = sharedTransitionScope, animatedContentScope = this)
    }
}

fun NavController.navigateToMovieListingPage(
    movieType: String,
    navOptions: NavOptions? = null
) = navigate("movie-listing/$movieType", navOptions)