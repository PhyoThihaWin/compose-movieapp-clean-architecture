package com.pthw.composemovieappcleanarchitecture.feature.payment

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 16/04/2024.
 */

const val paymentPageNavigationRoute = "payment-page"
fun NavGraphBuilder.paymentPage() {
    composable(
        route = paymentPageNavigationRoute
    ) {
        PaymentPage()
    }
}

fun NavController.navigateToPaymentPage(navOptions: NavOptions? = null) =
    navigate(paymentPageNavigationRoute, navOptions)