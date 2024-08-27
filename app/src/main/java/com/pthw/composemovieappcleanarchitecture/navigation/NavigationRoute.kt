package com.pthw.composemovieappcleanarchitecture.navigation

import kotlinx.serialization.Serializable

/**
 * Created by P.T.H.W on 19/08/2024.
 */
@Serializable
sealed class NavRoute {

    @Serializable
    data object HomePage : NavRoute()
    @Serializable
    data object FavoritePage : NavRoute()
    @Serializable
    data object MoviePage : NavRoute()
    @Serializable
    data object ProfilePage : NavRoute()

    @Serializable
    data object SearchMoviesPage : NavRoute()

    @Serializable
    data class MovieDetail(
        val sharedKey: String,
        val id: Int,
        val backdropPath: String
    ) : NavRoute()

}