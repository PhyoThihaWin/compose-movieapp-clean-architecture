package com.pthw.data.repository

import com.pthw.data.cache.database.AppDatabase
import com.pthw.data.cache.database.mapper.MovieEntityVoMapper
import com.pthw.data.cache.database.mapper.MovieVoEntityMapper
import com.pthw.data.network.home.HomeService
import com.pthw.data.network.home.mapper.ActorVoMapper
import com.pthw.data.network.home.mapper.MovieVoMapper
import com.pthw.domain.model.ActorVo
import com.pthw.domain.model.MovieVo
import com.pthw.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

/**
 * Created by P.T.H.W on 01/04/2024.
 */
class HomeRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val service: HomeService,
    private val movieVoMapper: MovieVoMapper,
    private val movieEntityVoMapper: MovieEntityVoMapper,
    private val movieVoEntityMapper: MovieVoEntityMapper,
    private val actorVoMapper: ActorVoMapper
) : HomeRepository {
    override suspend fun getNowPlayingMovies() {
        val raw = service.getNowPlayingMovies()
        val data = raw.data?.map(movieVoMapper::map)?.map(movieVoEntityMapper::map)
        database.movieDao().insertMovies(data.orEmpty())
    }

    override suspend fun getUpComingMovies(): List<MovieVo> {
        val raw = service.getUpComingMovies()
        return raw.data?.map(movieVoMapper::map).orEmpty()
    }

    override suspend fun getPopularMovies(): List<MovieVo> {
        val raw = service.getPopularMovies()
        return raw.data?.map(movieVoMapper::map).orEmpty()
    }

    override suspend fun getPopularPeople(): List<ActorVo> {
        val raw = service.getPopularPeople()
        return raw.data?.map(actorVoMapper::map).orEmpty()
    }

    override suspend fun getDbNowPlayingMovies(): Flow<List<MovieVo>> {
        coroutineScope {
            supervisorScope {
                launch {
                    try {
                        getNowPlayingMovies()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
        return database.movieDao().getAllMovies().map { list ->
            list.map(movieEntityVoMapper::map)
        }
    }
}