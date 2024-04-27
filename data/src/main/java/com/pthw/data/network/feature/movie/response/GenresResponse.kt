package com.pthw.data.network.feature.movie.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres") val genres: List<Genre?>?
) {
    @Serializable
    data class Genre(
        @SerialName("id") val id: Int?,
        @SerialName("name") val name: String?
    )
}