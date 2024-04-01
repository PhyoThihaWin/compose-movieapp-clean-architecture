package com.pthw.domain.repository

/**
 * Created by P.T.H.W on 01/04/2024.
 */
interface HomeRepository {
    suspend fun getNowPlayingMovies(): String
}