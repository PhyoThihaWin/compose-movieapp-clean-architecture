package com.pthw.data.local.realmdb.datasource_impl

import com.pthw.data.local.datasource.GenreDataSource
import com.pthw.data.local.datasource.MovieDataSource
import com.pthw.data.local.realmdb.entity.MovieRealmEntity
import com.pthw.data.network.movie.response.MovieResponse
import com.pthw.domain.home.model.MovieVo
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 05/09/2024.
 */
class MovieRealmDataSourceImpl @Inject constructor(
    private val realm: Realm,
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
            it.toMovieRealmEntity(
                genres = genres,
                isNowPlaying = isNowPlaying,
                isUpComing = isUpComing,
                isPopular = isPopular
            )
        }

        val favorites = getFavoriteMovies().first()
        val prepareData = data.map { item ->
            if (favorites.find { it.id == item.id } != null) {
                item.isFavorite = true
                item
            } else item
        }

        realm.write {
            when {
                isNowPlaying -> delete(query<MovieRealmEntity>("isNowPlaying == ${true}").find())
                isUpComing -> delete(query<MovieRealmEntity>("isUpComing == ${true}").find())
                else -> delete(query<MovieRealmEntity>("isPopular == ${true}").find())
            }
            prepareData.forEach { copyToRealm(it) }
        }
    }

    override fun getHomeMovies(
        isNowPlaying: Boolean,
        isUpComing: Boolean,
        isPopular: Boolean
    ): Flow<List<MovieVo>> {
        return realm.query<MovieRealmEntity>(
            "isNowPlaying == $0 AND isUpComing == $1 AND isPopular == $2",
            isNowPlaying,
            isUpComing,
            isPopular
        ).find().asFlow().map { it.list.toList().map { it.toMovieVo() } }
    }

    override suspend fun updateFavoriteMovie(movieId: Int) {
        realm.write {
            val results = query<MovieRealmEntity>("id == $movieId").find()
            results.forEach {
                it.isFavorite = !it.isFavorite
            }
        }
    }

    override fun getFavoriteMovies(): Flow<List<MovieVo>> {
        return realm.query<MovieRealmEntity>("isFavorite == ${true}").distinct("id").find().asFlow()
            .map { it.list.toList().map { it.toMovieVo() } }
    }

}