package com.pthw.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.data.network.feature.home.HomeService
import com.pthw.data.network.feature.home.mapper.MovieVoMapper
import com.pthw.data.network.feature.movie.pagingsource.MoviePagingSource
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */

private const val ITEMS_PER_PAGE = 10

class MovieRepositoryImpl @Inject constructor(
    private val service: HomeService,
    private val movieVoMapper: MovieVoMapper
) : MovieRepository {
    override fun getNowPlayingMovies(): Flow<PagingData<MovieVo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(service = service) }
        ).flow.map { pagingData ->
            pagingData.map { movieVoMapper.map(it) }
        }
    }
}