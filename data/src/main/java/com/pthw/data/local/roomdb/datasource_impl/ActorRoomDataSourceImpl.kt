package com.pthw.data.local.roomdb.datasource_impl

import androidx.room.withTransaction
import com.pthw.data.local.datasource.ActorDataSource
import com.pthw.data.local.roomdb.AppDatabase
import com.pthw.data.local.roomdb.entities.ActorEntity
import com.pthw.data.network.movie.response.ActorResponse
import com.pthw.domain.home.model.ActorVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/09/2024.
 */
class ActorRoomDataSourceImpl @Inject constructor(
    private val database: AppDatabase,
) : ActorDataSource {
    override suspend fun insertActors(list: List<ActorResponse>) {
        val data = list.map(ActorResponse::toActorEntity)
        database.withTransaction {
            database.actorDao().clearActors()
            database.actorDao().insertActors(data)
        }
    }

    override fun getAllActors(): Flow<List<ActorVo>> {
        return database.actorDao().getAllActors().map {
            it.map(ActorEntity::toActorVo)
        }
    }

}