package com.pthw.data.local.datasource

import com.pthw.data.network.movie.response.MovieResponse
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 03/09/2024.
 */
interface MovieDataSource {
    suspend fun insertMovies(
        list: List<MovieResponse>,
        isNowPlaying: Boolean = false,
        isUpComing: Boolean = false,
        isPopular: Boolean = false
    )

    fun getHomeMovies(
        isNowPlaying: Boolean = false,
        isUpComing: Boolean = false,
        isPopular: Boolean = false
    ): Flow<List<MovieVo>>

    suspend fun updateFavoriteMovie(movieId: Int)

    fun getFavoriteMovies(): Flow<List<MovieVo>>
}