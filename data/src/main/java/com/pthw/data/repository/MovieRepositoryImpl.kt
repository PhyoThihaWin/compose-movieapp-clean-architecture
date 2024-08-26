package com.pthw.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.pthw.data.local.database.AppDatabase
import com.pthw.data.local.database.entities.GenreEntity
import com.pthw.data.local.movie.MovieEntityVoMapper
import com.pthw.data.local.movie.MovieVoEntityMapper
import com.pthw.data.network.movie.MovieApiService
import com.pthw.data.network.movie.mapper.MovieDetailVoMapper
import com.pthw.data.network.movie.mapper.MovieVoMapper
import com.pthw.data.network.movie.pagingsource.NowPlayingMoviePagingSource
import com.pthw.data.network.movie.pagingsource.SearchMoviePagingSource
import com.pthw.data.network.movie.pagingsource.UpComingMoviePagingSource
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.domain.repository.MovieRepository
import com.pthw.shared.extension.orZero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */

private const val ITEMS_PER_PAGE = 10

class MovieRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val apiService: MovieApiService,
    private val movieVoMapper: MovieVoMapper,
    private val movieDetailVoMapper: MovieDetailVoMapper,
    private val movieEntityVoMapper: MovieEntityVoMapper,
    private val movieVoEntityMapper: MovieVoEntityMapper,
) : MovieRepository {

    override suspend fun fetchNowPlayingMovies() {
        val raw = apiService.getNowPlayingMovies()
        movieVoEntityMapper.prepareForNowPlaying()
        val genres = database.genreDao().getAllGenres().map {
            GenreVo(it.id, it.name)
        }
        val data = raw.data?.map {
            movieVoMapper.map(it, genres)
        }?.map(movieVoEntityMapper::map)

        database.withTransaction {
            val favorites = database.movieDao().getFavoriteMovies().first()
            database.movieDao().deleteNowPlayingMovies()
            database.movieDao().insertMovies(data?.map { item ->
                if (favorites.find { it.id == item.id } != null) item.copy(isFavorite = true) else item
            }.orEmpty())
        }
    }

    override suspend fun fetchUpComingMovies() {
        val raw = apiService.getUpComingMovies()
        movieVoEntityMapper.prepareForUpComing()
        val genres = database.genreDao().getAllGenres().map {
            GenreVo(it.id, it.name)
        }
        val data = raw.data?.map {
            movieVoMapper.map(it, genres)
        }?.map(movieVoEntityMapper::map)

        database.withTransaction {
            val favorites = database.movieDao().getFavoriteMovies().first()
            database.movieDao().deleteUpComingMovies()
            database.movieDao().insertMovies(data?.map { item ->
                if (favorites.find { it.id == item.id } != null) item.copy(isFavorite = true) else item
            }.orEmpty())
        }
    }

    override suspend fun fetchPopularMovies() {
        val raw = apiService.getPopularMovies()
        movieVoEntityMapper.prepareForPopular()
        val genres = database.genreDao().getAllGenres().map {
            GenreVo(it.id, it.name)
        }
        val data = raw.data?.map {
            movieVoMapper.map(it, genres)
        }?.map(movieVoEntityMapper::map)

        database.withTransaction {
            val favorites = database.movieDao().getFavoriteMovies().first()
            database.movieDao().deletePopularMovies()
            database.movieDao().insertMovies(data?.map { item ->
                if (favorites.find { it.id == item.id } != null) item.copy(isFavorite = true) else item
            }.orEmpty())
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

    override fun getDbFavoriteMovies(): Flow<List<MovieVo>> {
        return database.movieDao().getFavoriteMovies().map {
            it.map(movieEntityVoMapper::map)
        }
    }

    override suspend fun favoriteDbMovie(movieId: Int) {
        database.movieDao().updateFavoriteMovie(movieId)
    }


    override fun getNowPlayingPagingMovies(): Flow<PagingData<MovieVo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingMoviePagingSource(apiService = apiService) }
        ).flow.map { pagingData ->
            val genres = database.genreDao().getAllGenres().map {
                GenreVo(it.id, it.name)
            }
            pagingData.map { movieVoMapper.map(it, genres) }
        }
    }

    override fun getUpComingPagingMovies(): Flow<PagingData<MovieVo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UpComingMoviePagingSource(apiService = apiService) }
        ).flow.map { pagingData ->
            val genres = database.genreDao().getAllGenres().map {
                GenreVo(it.id, it.name)
            }
            pagingData.map { movieVoMapper.map(it, genres) }
        }
    }

    override fun searchPagingMovies(query: String): Flow<PagingData<MovieVo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchMoviePagingSource(query, apiService)
            }
        ).flow.map { pagingData ->
            val genres = database.genreDao().getAllGenres().map {
                GenreVo(it.id, it.name)
            }
            pagingData.map { movieVoMapper.map(it, genres) }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailVo {
        val rawDetails = apiService.getMovieDetails(movieId)
        val rawCasts = apiService.getMovieDetailCasts(movieId)

        val favorites = database.movieDao().getFavoriteMovies().first()
        return movieDetailVoMapper.map(rawDetails, rawCasts)
            .copy(isFavorite = favorites.find { it.id == movieId } != null)
    }

    override suspend fun getMovieGenres() {
        val raw = apiService.getMovieGenres()
        database.withTransaction {
            database.genreDao().clearGenres()
            database.genreDao().insertGenres(
                raw.genres?.map {
                    GenreEntity(
                        id = it?.id.orZero(),
                        name = it?.name.orEmpty()
                    )
                }.orEmpty()
            )
        }
    }

    override suspend fun getGenreById(id: Int): GenreVo {
        val genreEntity = database.genreDao().getGenreById(id)
        return GenreVo(
            id = genreEntity.id,
            name = genreEntity.name
        )
    }


}