package com.pthw.composemovieappcleanarchitecture.navigation

import com.pthw.composemovieappcleanarchitecture.R

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: NavRoute
) {
    HomePage(
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home,
        iconTextId = R.string.nav_title_home,
        titleTextId = R.string.nav_title_home,
        route = NavRoute.HomePage
    ),
    FavoritePage(
        selectedIcon = R.drawable.ic_ticket_selected,
        unselectedIcon = R.drawable.ic_ticket,
        iconTextId = R.string.nav_title_favorite,
        titleTextId = R.string.nav_title_favorite,
        route = NavRoute.FavoritePage
    ),
    MoviePage(
        selectedIcon = R.drawable.ic_video_selected,
        unselectedIcon = R.drawable.ic_video,
        iconTextId = R.string.nav_title_movie,
        titleTextId = R.string.nav_title_movie,
        route = NavRoute.MoviePage
    ),
    ProfilePage(
        selectedIcon = R.drawable.ic_user_selected,
        unselectedIcon = R.drawable.ic_user,
        iconTextId = R.string.nav_title_profile,
        titleTextId = R.string.nav_title_profile,
        route = NavRoute.ProfilePage
    ),
}
