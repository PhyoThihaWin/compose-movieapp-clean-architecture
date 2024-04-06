package com.pthw.domain.home.repository

import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 01/04/2024.
 */
interface HomeRepository {
    suspend fun getDbNowPlayingMovies(): Flow<List<MovieVo>>
    suspend fun getDbUpComingMovies(): Flow<List<MovieVo>>
    suspend fun getDbPopularMovies(): Flow<List<MovieVo>>
    suspend fun getDbPopularPeople(): Flow<List<ActorVo>>
}