package com.pthw.domain.movie.model

/**
 * Created by P.T.H.W on 24/04/2024.
 */
data class MovieCastVo(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
    val castId: Int,
    val character: String,
    val creditId: String,
    val order: Int
)