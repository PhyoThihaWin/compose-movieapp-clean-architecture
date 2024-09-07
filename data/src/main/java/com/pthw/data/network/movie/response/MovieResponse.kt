package com.pthw.data.network.movie.response


import com.pthw.data.local.roomdb.entities.MovieEntity
import com.pthw.data.local.realmdb.entity.MovieRealmEntity
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.shared.extension.orZero
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("adult") val adult: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int?>?,
    @SerialName("id") val id: Int?,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("original_title") val originalTitle: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("popularity") val popularity: Double?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("title") val title: String?,
    @SerialName("video") val video: Boolean?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?
) {
    fun toMovieEntity(
        genres: List<GenreVo>,
        isNowPlaying: Boolean = false,
        isUpComing: Boolean = false,
        isPopular: Boolean = false
    ) = MovieEntity(
        id = id.orZero(),
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        backdropPath = IMAGE_BASE_URL + backdropPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        posterPath = IMAGE_BASE_URL + posterPath.orEmpty(),
        voteAverage = voteAverage.orZero(),
        genreIds = genreIds?.map {
            genres.find { genre -> genre.id == it }?.name.orEmpty()
        }.orEmpty(),
        isFavorite = false,

        isNowPlaying = isNowPlaying,
        isUpComing = isUpComing,
        isPopular = isPopular
    )

    fun toMovieRealmEntity(
        genres: List<GenreVo>,
        isNowPlaying: Boolean = false,
        isUpComing: Boolean = false,
        isPopular: Boolean = false
    ) = MovieRealmEntity().apply {
        id = this@MovieResponse.id.orZero()
        title = this@MovieResponse.title.orEmpty()
        overview = this@MovieResponse.overview.orEmpty()
        backdropPath = IMAGE_BASE_URL + this@MovieResponse.backdropPath.orEmpty()
        releaseDate = this@MovieResponse.releaseDate.orEmpty()
        posterPath = IMAGE_BASE_URL + this@MovieResponse.posterPath.orEmpty()
        voteAverage = this@MovieResponse.voteAverage.orZero()
        genreIds = this@MovieResponse.genreIds?.map {
            genres.find { genre -> genre.id == it }?.name.orEmpty()
        }.orEmpty().toRealmList()
        isFavorite = false

        this.isNowPlaying = isNowPlaying
        this.isUpComing = isUpComing
        this.isPopular = isPopular
    }

    fun toMovieVo(genres: List<GenreVo>) = MovieVo(
        id = id.orZero(),
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        backdropPath = IMAGE_BASE_URL + backdropPath.orEmpty(),
        posterPath = IMAGE_BASE_URL + posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage.orZero().toFloat(),
        genreIds = genreIds?.map {
            genres.find { genre -> genre.id == it }?.name.orEmpty()
        }.orEmpty(),
    )
}