package com.pthw.data.network.movie.response


import com.pthw.data.local.roomdb.entities.ActorEntity
import com.pthw.data.local.realmdb.entity.ActorRealmEntity
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.shared.extension.orFalse
import com.pthw.shared.extension.orZero
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

    fun toActorRealmEntity() = ActorRealmEntity().apply {
        adult = this@ActorResponse.adult.orFalse()
        gender = this@ActorResponse.gender.orZero()
        id = this@ActorResponse.id ?: 0
        knownForDepartment = this@ActorResponse.knownForDepartment.toString()
        name = this@ActorResponse.name.orEmpty()
        originalName = this@ActorResponse.originalName.orEmpty()
        popularity = this@ActorResponse.popularity.orZero()
        profilePath = IMAGE_BASE_URL + this@ActorResponse.profilePath.orEmpty()
    }

    fun toActorEntity() = ActorEntity(
        adult = adult.orFalse(),
        gender = gender.orZero(),
        id = id.orZero(),
        knownForDepartment = knownForDepartment.orEmpty(),
        name = name.orEmpty(),
        originalName = originalName.orEmpty(),
        popularity = popularity.orZero(),
        profilePath = IMAGE_BASE_URL + profilePath
    )
}