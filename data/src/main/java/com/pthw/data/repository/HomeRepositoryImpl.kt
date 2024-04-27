package com.pthw.data.repository

import androidx.room.withTransaction
import com.pthw.data.cache.database.AppDatabase
import com.pthw.data.cache.home.mapper.ActorEntityVoMapper
import com.pthw.data.cache.home.mapper.ActorVoEntityMapper
import com.pthw.data.cache.home.mapper.MovieEntityVoMapper
import com.pthw.data.cache.home.mapper.MovieVoEntityMapper
import com.pthw.data.network.feature.home.HomeService
import com.pthw.data.network.feature.home.mapper.ActorVoMapper
import com.pthw.data.network.feature.home.mapper.MovieVoMapper
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.home.repository.HomeRepository
import com.pthw.domain.movie.model.GenreVo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
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
    private val actorVoMapper: ActorVoMapper,
    private val actorEntityVoMapper: ActorEntityVoMapper,
    private val actorVoEntityMapper: ActorVoEntityMapper
) : HomeRepository {

    private suspend fun getNowPlayingMovies() {
        val raw = service.getNowPlayingMovies()
        movieVoEntityMapper.prepareForNowPlaying()
        val genres = database.genreDao().getAllGenres().map {
            GenreVo(it.id, it.name)
        }
        val data = raw.data?.map {
            movieVoMapper.map(it, genres)
        }?.map(movieVoEntityMapper::map)
        database.withTransaction {
            database.movieDao().deleteNowPlayingMovies()
            database.movieDao().insertMovies(data.orEmpty())
        }
    }

    private suspend fun getUpComingMovies() {
        val raw = service.getUpComingMovies()
        movieVoEntityMapper.prepareForUpComing()
         val genres = database.genreDao().getAllGenres().map {
            GenreVo(it.id, it.name)
        }
        val data = raw.data?.map {
            movieVoMapper.map(it, genres)
        }?.map(movieVoEntityMapper::map)
        database.withTransaction {
            database.movieDao().deleteUpComingMovies()
            database.movieDao().insertMovies(data.orEmpty())
        }
    }

    private suspend fun getPopularMovies() {
        val raw = service.getPopularMovies()
        movieVoEntityMapper.prepareForPopular()
         val genres = database.genreDao().getAllGenres().map {
            GenreVo(it.id, it.name)
        }
        val data = raw.data?.map {
            movieVoMapper.map(it, genres)
        }?.map(movieVoEntityMapper::map)
        database.withTransaction {
            database.movieDao().deletePopularMovies()
            database.movieDao().insertMovies(data.orEmpty())
        }
    }

    private suspend fun getPopularPeople() {
        val raw = service.getPopularPeople()
        val data = raw.data?.map(actorVoMapper::map)?.map(actorVoEntityMapper::map)
        database.withTransaction {
            database.actorDao().clearActors()
            database.actorDao().insertActors(data.orEmpty())
        }
    }

    override suspend fun fetchHomeData() {
        coroutineScope {
            async { getNowPlayingMovies() }.await()
            async { getUpComingMovies() }.await()
            async { getPopularMovies() }.await()
            async { getPopularPeople() }.await()
        }
    }

    override fun getDbNowPlayingMovies(): Flow<List<MovieVo>> {
        return database.movieDao().getHomeMovies(isNowPlaying = true).map { list ->
            list.map(movieEntityVoMapper::map)
        }
    }

    override fun getDbUpComingMovies(): Flow<List<MovieVo>> {
        return database.movieDao().getHomeMovies(isUpComing = true).map { list ->
            list.map(movieEntityVoMapper::map)
        }
    }

    override fun getDbPopularMovies(): Flow<List<MovieVo>> {
        return database.movieDao().getHomeMovies(isPopular = true).map { list ->
            list.map(movieEntityVoMapper::map)
        }
    }

    override fun getDbPopularPeople(): Flow<List<ActorVo>> {
        return database.actorDao().getAllActors().map { list ->
            list.map(actorEntityVoMapper::map)
        }
    }
}