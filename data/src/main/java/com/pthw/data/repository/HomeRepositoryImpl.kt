package com.pthw.data.repository

import com.pthw.data.network.home.HomeService
import com.pthw.domain.repository.HomeRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 01/04/2024.
 */
class HomeRepositoryImpl @Inject constructor(
    private val service: HomeService,
) : HomeRepository {
    override suspend fun getNowPlayingMovies(): String {
        return service.getNowPlayingMovies()
    }
}