package com.pthw.domain.model

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
