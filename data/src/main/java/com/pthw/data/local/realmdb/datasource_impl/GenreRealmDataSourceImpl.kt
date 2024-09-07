package com.pthw.data.local.realmdb.datasource_impl

import com.pthw.data.local.datasource.GenreDataSource
import com.pthw.data.local.realmdb.entity.GenreRealmEntity
import com.pthw.data.network.movie.response.GenresResponse
import com.pthw.data.network.movie.response.toGenreRealEntity
import com.pthw.domain.movie.model.GenreVo
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/09/2024.
 */
class GenreRealmDataSourceImpl @Inject constructor(
    private val realm: Realm
) : GenreDataSource {
    override suspend fun insertGenres(list: List<GenresResponse.Genre?>) {
        realm.write {
            delete(GenreRealmEntity::class)
            list.forEach {
                copyToRealm(it.toGenreRealEntity())
            }
        }
    }

    override suspend fun getGenreById(id: Int): GenreVo {
        return realm.query<GenreRealmEntity>().find().first().toGenreVo()
    }

    override suspend fun getAllGenres(): List<GenreVo> {
        return realm.query<GenreRealmEntity>().find().map(GenreRealmEntity::toGenreVo)
    }
}