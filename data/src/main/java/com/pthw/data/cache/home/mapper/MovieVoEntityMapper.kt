package com.pthw.data.cache.home.mapper

import com.pthw.data.cache.database.entities.MovieEntity
import com.pthw.domain.home.model.MovieVo
import com.pthw.shared.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class MovieVoEntityMapper @Inject constructor() : UnidirectionalMap<MovieVo, MovieEntity> {
    private var isNowPlaying = false
    private var isUpComing = false
    private var isPopular = false

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
            isFavorite = item.isFavorite,

            isNowPlaying = this.isNowPlaying,
            isUpComing = this.isUpComing,
            isPopular = this.isPopular
        )
    }

    fun prepareForNowPlaying() {
        isNowPlaying = true
        isUpComing = false
        isPopular = false
    }

    fun prepareForUpComing() {
        isNowPlaying = false
        isUpComing = true
        isPopular = false
    }

    fun prepareForPopular() {
        isNowPlaying = false
        isUpComing = false
        isPopular = true
    }
}