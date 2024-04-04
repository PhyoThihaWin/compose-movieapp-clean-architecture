package com.pthw.composemovieappcleanarchitecture.feature.ticket

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 25/03/2024.
 */

const val ticketNavPageNavigationRoute = "TICKET"
fun NavGraphBuilder.ticketNavPage() {
    composable(
        route = ticketNavPageNavigationRoute,
    ) {
        TicketNavPage()
    }
}

fun NavController.navigateToTicketNavPage(navOptions: NavOptions? = null) =
    navigate(ticketNavPageNavigationRoute, navOptions)
