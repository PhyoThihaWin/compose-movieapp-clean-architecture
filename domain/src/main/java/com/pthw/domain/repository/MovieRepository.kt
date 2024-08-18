package com.pthw.domain.repository

import androidx.paging.PagingData
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.domain.movie.model.MovieDetailVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 06/04/2024.
 */
interface MovieRepository {
    suspend fun fetchNowPlayingMovies()
    suspend fun fetchUpComingMovies()
    suspend fun fetchPopularMovies()

    fun getDbNowPlayingMovies(): Flow<List<MovieVo>>
    fun getDbUpComingMovies(): Flow<List<MovieVo>>
    fun getDbPopularMovies(): Flow<List<MovieVo>>

    fun getNowPlayingPagingMovies(): Flow<PagingData<MovieVo>>
    fun getUpComingPagingMovies(): Flow<PagingData<MovieVo>>

    suspend fun getMovieDetails(movieId: String): MovieDetailVo

    // movie genre
    suspend fun getMovieGenres()
    suspend fun getGenreById(id: Int): GenreVo
}