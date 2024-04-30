package com.pthw.composemovieappcleanarchitecture.feature.cinemaseat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 14/04/2024.
 */

const val cinemaSeatPageNavigationRoute = "cinema-seat"
fun NavGraphBuilder.cinemaSeatPage() {
    composable(
        route = cinemaSeatPageNavigationRoute
    ) {
        CinemaSeatPage()
    }
}

fun NavController.navigateToCinemaSeatPage(navOptions: NavOptions? = null) =
    navigate(cinemaSeatPageNavigationRoute)