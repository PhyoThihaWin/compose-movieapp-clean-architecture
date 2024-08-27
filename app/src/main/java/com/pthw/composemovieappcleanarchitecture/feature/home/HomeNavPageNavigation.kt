package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.navigation.NavRoute

/**
 * Created by P.T.H.W on 27/03/2024.
 */


@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeNavPage(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<NavRoute.HomePage> {
        HomeNavPage(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this
        )
    }
}

fun NavController.navigateToHomeNavPage(navOptions: NavOptions? = null) =
    navigate(NavRoute.HomePage, navOptions)