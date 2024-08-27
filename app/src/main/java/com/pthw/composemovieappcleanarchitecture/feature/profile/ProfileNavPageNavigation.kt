package com.pthw.composemovieappcleanarchitecture.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pthw.composemovieappcleanarchitecture.navigation.NavRoute

/**
 * Created by P.T.H.W on 27/03/2024.
 */

fun NavGraphBuilder.profileNavPage() {
    composable<NavRoute.ProfilePage> {
        ProfileNavPage()
    }
}

fun NavController.navigateToProfileNavPage(navOptions: NavOptions? = null) =
    navigate(NavRoute.ProfilePage, navOptions)