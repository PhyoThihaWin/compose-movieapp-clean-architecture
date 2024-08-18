package com.pthw.data.repository

import androidx.room.withTransaction
import com.pthw.data.local.actor.ActorEntityVoMapper
import com.pthw.data.local.actor.ActorVoEntityMapper
import com.pthw.data.local.database.AppDatabase
import com.pthw.data.network.movie.MovieApiService
import com.pthw.data.network.movie.mapper.ActorVoMapper
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val apiService: MovieApiService,
    private val actorVoMapper: ActorVoMapper,
    private val actorEntityVoMapper: ActorEntityVoMapper,
    private val actorVoEntityMapper: ActorVoEntityMapper
) : ActorRepository {
    override suspend fun fetchPopularPeople() {
        val raw = apiService.getPopularPeople()
        val data = raw.data?.map(actorVoMapper::map)?.map(actorVoEntityMapper::map)
        database.withTransaction {
            database.actorDao().clearActors()
            database.actorDao().insertActors(data.orEmpty())
        }
    }

    override fun getDbPopularPeople(): Flow<List<ActorVo>> {
        return database.actorDao().getAllActors().map { list ->
            list.map(actorEntityVoMapper::map)
        }
    }
}