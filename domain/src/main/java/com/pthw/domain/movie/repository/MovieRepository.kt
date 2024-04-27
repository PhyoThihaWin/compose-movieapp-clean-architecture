package com.pthw.domain.movie.repository

import androidx.paging.PagingData
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.domain.movie.model.MovieDetailVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 06/04/2024.
 */
interface MovieRepository {
    fun getNowPlayingMovies(): Flow<PagingData<MovieVo>>
    suspend fun getMovieDetails(movieId: String): MovieDetailVo

    // movie genre
    suspend fun getMovieGenres()
    suspend fun getGenreById(id: Int): GenreVo
}