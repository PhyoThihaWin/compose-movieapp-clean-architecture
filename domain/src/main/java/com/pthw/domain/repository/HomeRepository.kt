package com.pthw.domain.repository

import com.pthw.domain.model.ActorVo
import com.pthw.domain.model.MovieVo
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