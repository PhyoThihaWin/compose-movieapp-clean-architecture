package com.pthw.data.network.movie.mapper

import com.pthw.data.network.movie.response.MovieResponse
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.shared.extension.orZero
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class MovieVoMapper @Inject constructor() {
    fun map(item: MovieResponse?, genres: List<GenreVo>): MovieVo {
        return MovieVo(
            id = item?.id.orZero(),
            title = item?.title.orEmpty(),
            overview = item?.overview.orEmpty(),
            backdropPath = IMAGE_BASE_URL + item?.backdropPath.orEmpty(),
            posterPath = IMAGE_BASE_URL + item?.posterPath.orEmpty(),
            releaseDate = item?.releaseDate.orEmpty(),
            voteAverage = item?.voteAverage.orZero().toFloat(),
            genreIds = item?.genreIds?.map {
                genres.find { genre -> genre.id == it }?.name.orEmpty()
            }.orEmpty(),
        )
    }
}