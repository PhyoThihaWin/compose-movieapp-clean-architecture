package com.pthw.composemovieappcleanarchitecture.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.pthw.composemovieappcleanarchitecture.feature.home.homeNavPage
import com.pthw.composemovieappcleanarchitecture.feature.home.homeNavPageNavigationRoute
import com.pthw.composemovieappcleanarchitecture.feature.movie.movieNavPage
import com.pthw.composemovieappcleanarchitecture.feature.movie.movieNavPageNavigationRoute
import com.pthw.composemovieappcleanarchitecture.feature.profile.profileNavPage
import com.pthw.composemovieappcleanarchitecture.feature.ticket.ticketNavPage

/**
 * Created by P.T.H.W on 25/03/2024.
 */

@Composable
fun MainNavHost(
    modifier: Modifier,
    appState: MainPageState,
    startDestination: String = homeNavPageNavigationRoute
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeNavPage()
        ticketNavPage()
        movieNavPage()
        profileNavPage()
    }
}