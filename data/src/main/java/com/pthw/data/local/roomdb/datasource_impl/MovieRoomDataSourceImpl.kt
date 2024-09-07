package com.pthw.data.local.roomdb.datasource_impl

import androidx.room.withTransaction
import com.pthw.data.local.datasource.GenreDataSource
import com.pthw.data.local.datasource.MovieDataSource
import com.pthw.data.local.roomdb.AppDatabase
import com.pthw.data.local.roomdb.entities.MovieEntity
import com.pthw.data.network.movie.response.MovieResponse
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/09/2024.
 */
class MovieRoomDataSourceImpl @Inject constructor(
    private val database: AppDatabase,
    private val genreDataSource: GenreDataSource
) : MovieDataSource {

    override suspend fun insertMovies(
        list: List<MovieResponse>,
        isNowPlaying: Boolean,
        isUpComing: Boolean,
        isPopular: Boolean
    ) {
        val genres = genreDataSource.getAllGenres()
        val data = list.map {
            it.toMovieEntity(
                genres = genres,
                isNowPlaying = isNowPlaying,
                isUpComing = isUpComing,
                isPopular = isPopular
            )
        }

        val favorites = getFavoriteMovies().first()
        val prepareData = data.map { item ->
            if (favorites.find { it.id == item.id } != null)
                item.copy(isFavorite = true)
            else item
        }

        database.withTransaction {
            when {
                isNowPlaying -> database.movieDao().deleteNowPlayingMovies()
                isUpComing -> database.movieDao().deleteUpComingMovies()
                else -> database.movieDao().deletePopularMovies()
            }
            database.movieDao().insertMovies(prepareData)
        }
    }


    override fun getHomeMovies(
        isNowPlaying: Boolean,
        isUpComing: Boolean,
        isPopular: Boolean
    ): Flow<List<MovieVo>> {
        return database.movieDao().getHomeMovies(
            isNowPlaying = isNowPlaying,
            isUpComing = isUpComing,
            isPopular = isPopular
        ).map { it.map(MovieEntity::toMovieVo) }
    }


    override suspend fun updateFavoriteMovie(movieId: Int) {
        database.movieDao().updateFavoriteMovie(movieId)
    }

    override fun getFavoriteMovies(): Flow<List<MovieVo>> {
        return database.movieDao().getFavoriteMovies().map { it.map(MovieEntity::toMovieVo) }
    }
}