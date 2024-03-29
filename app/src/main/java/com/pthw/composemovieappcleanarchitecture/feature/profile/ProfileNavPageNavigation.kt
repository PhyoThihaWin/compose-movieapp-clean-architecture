package com.pthw.composemovieappcleanarchitecture.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.feature.movie.MovieNavPage

/**
 * Created by P.T.H.W on 27/03/2024.
 */

const val profileNavPageNavigationRoute = "profile/"
fun NavGraphBuilder.profileNavPage() {
    composable(
        route = profileNavPageNavigationRoute,
    ) {
        ProfileNavPage()
    }
}

fun NavController.navigateToProfileNavPage(navOptions: NavOptions? = null) =
    navigate(profileNavPageNavigationRoute, navOptions)