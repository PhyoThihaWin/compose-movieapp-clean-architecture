package com.pthw.composemovieappcleanarchitecture.feature.ticket

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 19/04/2024.
 */

const val invoiceTicketPageNavigationRoute = "invoice-ticket"
fun NavGraphBuilder.invoiceTicketPage(
    navController: NavController
) {
    composable(
        route = invoiceTicketPageNavigationRoute,
    ) {
        InvoiceTicketPage(navController = navController)
    }
}

fun NavController.navigateToInvoiceTicketPage(navOptions: NavOptions? = null) =
    navigate(invoiceTicketPageNavigationRoute, navOptions)
