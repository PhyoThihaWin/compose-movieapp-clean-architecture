package com.pthw.composemovieappcleanarchitecture.feature.search

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.navigation.NavRoute

/**
 * Created by P.T.H.W on 23/08/2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.searchMoviesPage(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<NavRoute.SearchMoviesPage> {
        SearchMoviesPage(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToSearchMoviesPage(navOptions: NavOptions? = null) =
    navigate(NavRoute.SearchMoviesPage, navOptions)