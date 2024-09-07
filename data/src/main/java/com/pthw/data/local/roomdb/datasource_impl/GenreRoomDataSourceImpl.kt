package com.pthw.data.local.roomdb.datasource_impl

import androidx.room.withTransaction
import com.pthw.data.local.datasource.GenreDataSource
import com.pthw.data.local.roomdb.AppDatabase
import com.pthw.data.local.roomdb.entities.GenreEntity
import com.pthw.data.network.movie.response.GenresResponse
import com.pthw.data.network.movie.response.toGenreEntity
import com.pthw.domain.movie.model.GenreVo
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/09/2024.
 */
class GenreRoomDataSourceImpl @Inject constructor(
    private val database: AppDatabase
) : GenreDataSource {
    override suspend fun insertGenres(list: List<GenresResponse.Genre?>) {
        database.withTransaction {
            database.genreDao().clearGenres()
            database.genreDao().insertGenres(
                list.map {
                    it.toGenreEntity()
                }
            )
        }
    }

    override suspend fun getGenreById(id: Int): GenreVo {
        return database.genreDao().getGenreById(id).toGenreVo()
    }

    override suspend fun getAllGenres(): List<GenreVo> {
        return database.genreDao().getAllGenres().map(GenreEntity::toGenreVo)
    }
}