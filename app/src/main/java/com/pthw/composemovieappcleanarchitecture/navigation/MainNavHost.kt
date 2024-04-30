package com.pthw.composemovieappcleanarchitecture.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pthw.composemovieappcleanarchitecture.feature.cinemaseat.cinemaSeatPage
import com.pthw.composemovieappcleanarchitecture.feature.home.homeNavPage
import com.pthw.composemovieappcleanarchitecture.feature.home.homeNavPageNavigationRoute
import com.pthw.composemovieappcleanarchitecture.feature.listing.movieListingPage
import com.pthw.composemovieappcleanarchitecture.feature.movie.movieNavPage
import com.pthw.composemovieappcleanarchitecture.feature.movie.movieNavPageNavigationRoute
import com.pthw.composemovieappcleanarchitecture.feature.moviedetail.movieDetailPage
import com.pthw.composemovieappcleanarchitecture.feature.payment.paymentPage
import com.pthw.composemovieappcleanarchitecture.feature.profile.profileNavPage
import com.pthw.composemovieappcleanarchitecture.feature.ticket.invoiceTicketPage
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

    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeNavPage()
        ticketNavPage()
        movieNavPage()
        profileNavPage()
        movieListingPage()
        movieDetailPage()
        cinemaSeatPage()
        paymentPage()
        invoiceTicketPage()
    }
}