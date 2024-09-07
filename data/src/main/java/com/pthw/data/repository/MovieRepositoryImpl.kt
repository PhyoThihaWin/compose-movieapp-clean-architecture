package com.pthw.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.data.local.datasource.GenreDataSource
import com.pthw.data.local.datasource.MovieDataSource
import com.pthw.data.network.movie.MovieApiService
import com.pthw.data.network.movie.mapper.MovieDetailVoMapper
import com.pthw.data.network.movie.pagingsource.NowPlayingMoviePagingSource
import com.pthw.data.network.movie.pagingsource.SearchMoviePagingSource
import com.pthw.data.network.movie.pagingsource.UpComingMoviePagingSource
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */

private const val ITEMS_PER_PAGE = 10

class MovieRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val genreDataSource: GenreDataSource,
    private val apiService: MovieApiService,
    private val movieDetailVoMapper: MovieDetailVoMapper
) : MovieRepository {

    override suspend fun fetchNowPlayingMovies() {
        val raw = apiService.getNowPlayingMovies()
        movieDataSource.insertMovies(raw.data.orEmpty(), isNowPlaying = true)

    }

    override suspend fun fetchUpComingMovies() {
        val raw = apiService.getUpComingMovies()
        movieDataSource.insertMovies(raw.data.orEmpty(), isUpComing = true)
    }

    override suspend fun fetchPopularMovies() {
        val raw = apiService.getPopularMovies()
        movieDataSource.insertMovies(raw.data.orEmpty(), isPopular = true)
    }

    override fun getDbNowPlayingMovies(): Flow<List<MovieVo>> {
        return movieDataSource.getHomeMovies(isNowPlaying = true)
    }

    override fun getDbUpComingMovies(): Flow<List<MovieVo>> {
        return movieDataSource.getHomeMovies(isUpComing = true)
    }

    override fun getDbPopularMovies(): Flow<List<MovieVo>> {
        return movieDataSource.getHomeMovies(isPopular = true)
    }

    override fun getDbFavoriteMovies(): Flow<List<MovieVo>> {
        return movieDataSource.getFavoriteMovies()
    }

    override suspend fun favoriteDbMovie(movieId: Int) {
        movieDataSource.updateFavoriteMovie(movieId)
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
            val genres = genreDataSource.getAllGenres()
            pagingData.map { it.toMovieVo(genres) }
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
            val genres = genreDataSource.getAllGenres()
            pagingData.map { it.toMovieVo(genres) }
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
            val genres = genreDataSource.getAllGenres()
            pagingData.map { it.toMovieVo(genres) }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailVo {
        val rawDetails = apiService.getMovieDetails(movieId)
        val rawCasts = apiService.getMovieDetailCasts(movieId)

        val favorites = movieDataSource.getFavoriteMovies().first()
        return movieDetailVoMapper.map(rawDetails, rawCasts)
            .copy(isFavorite = favorites.find { it.id == movieId } != null)
    }

    override suspend fun getMovieGenres() {
        val raw = apiService.getMovieGenres()
        genreDataSource.insertGenres(raw.genres.orEmpty())
    }

    override suspend fun getGenreById(id: Int): GenreVo {
        val genreEntity = genreDataSource.getGenreById(id)
        return GenreVo(
            id = genreEntity.id,
            name = genreEntity.name
        )
    }


}