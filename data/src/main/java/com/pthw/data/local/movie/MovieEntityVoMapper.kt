package com.pthw.data.local.movie

import com.pthw.data.local.database.entities.MovieEntity
import com.pthw.domain.home.model.MovieVo
import com.pthw.shared.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class MovieEntityVoMapper @Inject constructor() : UnidirectionalMap<MovieEntity, MovieVo> {
    override fun map(item: MovieEntity): MovieVo {
        return MovieVo(
            id = item.id,
            title = item.title,
            overview = item.overview,
            backdropPath = item.backdropPath,
            releaseDate = item.releaseDate,
            posterPath = item.posterPath,
            voteAverage = item.voteAverage.toFloat(),
            genreIds = item.genreIds,
            isFavorite = item.isFavorite
        )
    }
}