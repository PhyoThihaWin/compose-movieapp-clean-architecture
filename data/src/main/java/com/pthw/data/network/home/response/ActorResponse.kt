package com.pthw.data.network.home.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorResponse(
    @SerialName("adult") val adult: Boolean?,
    @SerialName("gender") val gender: Int?,
    @SerialName("id") val id: Int?,
    @SerialName("known_for_department") val knownForDepartment: String?,
    @SerialName("name") val name: String?,
    @SerialName("original_name") val originalName: String?,
    @SerialName("popularity") val popularity: Double?,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("known_for") val knownFor: List<KnownFor?>?
) {
    @Serializable
    data class KnownFor(
        @SerialName("adult") val adult: Boolean?,
        @SerialName("backdrop_path") val backdropPath: String?,
        @SerialName("id") val id: Int?,
        @SerialName("title") val title: String?,
        @SerialName("original_language") val originalLanguage: String?,
        @SerialName("original_title") val originalTitle: String?,
        @SerialName("overview") val overview: String?,
        @SerialName("poster_path") val posterPath: String?,
        @SerialName("media_type") val mediaType: String?,
        @SerialName("genre_ids") val genreIds: List<Int?>?,
        @SerialName("popularity") val popularity: Double?,
        @SerialName("release_date") val releaseDate: String?,
        @SerialName("video") val video: Boolean?,
        @SerialName("vote_average") val voteAverage: Double?,
        @SerialName("vote_count") val voteCount: Int?
    )
}