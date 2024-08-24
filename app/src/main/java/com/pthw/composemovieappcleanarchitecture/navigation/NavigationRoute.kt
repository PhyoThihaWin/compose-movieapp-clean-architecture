package com.pthw.composemovieappcleanarchitecture.navigation

import kotlinx.serialization.Serializable

/**
 * Created by P.T.H.W on 19/08/2024.
 */
@Serializable
sealed class Routes {

    @Serializable
    data object HomePage : Routes()

    @Serializable
    data object SearchMoviesPage : Routes()

    @Serializable
    data class MovieDetail(
        val sharedKey: String,
        val id: Int,
        val backdropPath: String
    ) : Routes()

}