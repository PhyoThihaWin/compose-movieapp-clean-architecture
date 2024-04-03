package com.pthw.data.cache.database.mapper

import com.pthw.data.cache.database.entities.MovieEntity
import com.pthw.domain.model.MovieVo
import com.pthw.shared.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class MovieVoEntityMapper @Inject constructor() : UnidirectionalMap<MovieVo, MovieEntity> {
    override fun map(item: MovieVo): MovieEntity {
        return MovieEntity(
            id = item.id,
            title = item.title,
            overview = item.overview,
            backdropPath = item.backdropPath,
            releaseDate = item.releaseDate,
            posterPath = item.posterPath,
            voteAverage = item.voteAverage,
            genreIds = item.genreIds,
            isFavorite = item.isFavorite
        )
    }
}