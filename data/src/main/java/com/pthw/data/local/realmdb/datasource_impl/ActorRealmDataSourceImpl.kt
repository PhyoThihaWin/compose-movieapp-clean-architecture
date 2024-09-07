package com.pthw.data.local.realmdb.datasource_impl

import com.pthw.data.local.datasource.ActorDataSource
import com.pthw.data.local.realmdb.entity.ActorRealmEntity
import com.pthw.data.network.movie.response.ActorResponse
import com.pthw.domain.home.model.ActorVo
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/09/2024.
 */
class ActorRealmDataSourceImpl @Inject constructor(
    private val realm: Realm
) : ActorDataSource {
    override suspend fun insertActors(list: List<ActorResponse>) {
        realm.write {
            delete(ActorRealmEntity::class)
            list.map { it.toActorRealmEntity() }.forEach { copyToRealm(it) }
        }
    }

    override fun getAllActors(): Flow<List<ActorVo>> {
        return realm.query<ActorRealmEntity>().asFlow()
            .map { it.list.map { it.toActorVo() } }
    }
}