package com.pthw.domain.home.model

/**
 * Created by P.T.H.W on 02/04/2024.
 */
data class ActorVo(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)
