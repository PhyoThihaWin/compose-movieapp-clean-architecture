package com.pthw.domain.repository

import com.pthw.domain.model.ActorVo
import com.pthw.domain.model.MovieVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 01/04/2024.
 */
interface HomeRepository {
    suspend fun getNowPlayingMovies()
    suspend fun getUpComingMovies(): List<MovieVo>
    suspend fun getPopularMovies(): List<MovieVo>
    suspend fun getPopularPeople(): List<ActorVo>

    suspend fun getDbNowPlayingMovies(): Flow<List<MovieVo>>
}