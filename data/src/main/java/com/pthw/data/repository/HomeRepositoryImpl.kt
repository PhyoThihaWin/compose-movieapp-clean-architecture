package com.pthw.data.repository

import com.pthw.data.network.home.HomeService
import com.pthw.data.network.home.mapper.MovieVoMapper
import com.pthw.domain.model.MovieVo
import com.pthw.domain.repository.HomeRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 01/04/2024.
 */
class HomeRepositoryImpl @Inject constructor(
    private val service: HomeService,
    private val movieVoMapper: MovieVoMapper
) : HomeRepository {
    override suspend fun getNowPlayingMovies(): List<MovieVo> {
        val raw = service.getNowPlayingMovies()
        return raw.data?.map(movieVoMapper::map).orEmpty()
    }

    override suspend fun getUpComingMovies(): List<MovieVo> {
        val raw = service.getUpComingMovies()
        return raw.data?.map(movieVoMapper::map).orEmpty()
    }
}