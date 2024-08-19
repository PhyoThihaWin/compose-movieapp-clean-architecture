package com.pthw.domain.home.model

import kotlinx.serialization.Serializable

/**
 * Created by P.T.H.W on 02/04/2024.
 */
@Serializable
data class MovieVo(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val genreIds: List<String>,
    val isFavorite: Boolean = false
): java.io.Serializable
