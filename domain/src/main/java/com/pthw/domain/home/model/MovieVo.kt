package com.pthw.domain.home.model

/**
 * Created by P.T.H.W on 02/04/2024.
 */
data class MovieVo(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val isFavorite: Boolean = false
)
