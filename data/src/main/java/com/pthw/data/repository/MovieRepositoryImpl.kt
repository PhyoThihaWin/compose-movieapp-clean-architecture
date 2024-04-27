package com.pthw.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.pthw.data.cache.database.AppDatabase
import com.pthw.data.cache.database.entities.GenreEntity
import com.pthw.data.network.feature.home.HomeService
import com.pthw.data.network.feature.home.mapper.MovieVoMapper
import com.pthw.data.network.feature.movie.MovieService
import com.pthw.data.network.feature.movie.mapper.MovieDetailVoMapper
import com.pthw.data.network.feature.movie.pagingsource.MoviePagingSource
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.GenreVo
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.domain.movie.repository.MovieRepository
import com.pthw.shared.extension.orZero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */

private const val ITEMS_PER_PAGE = 10

class MovieRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val service: HomeService,
    private val movieService: MovieService,
    private val movieVoMapper: MovieVoMapper,
    private val movieDetailVoMapper: MovieDetailVoMapper
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
            val genres = database.genreDao().getAllGenres().map {
                GenreVo(it.id, it.name)
            }
            pagingData.map { movieVoMapper.map(it, genres) }
        }
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetailVo {
        val rawDetails = movieService.getMovieDetails(movieId)
        val rawCasts = movieService.getMovieDetailCasts(movieId)
        return movieDetailVoMapper.map(rawDetails, rawCasts)
    }

    override suspend fun getMovieGenres() {
        val raw = movieService.getMovieGenres()
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