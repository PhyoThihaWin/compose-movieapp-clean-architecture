package com.pthw.data.local.datasource

import com.pthw.data.network.movie.response.ActorResponse
import com.pthw.domain.home.model.ActorVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 06/09/2024.
 */
interface ActorDataSource {
    suspend fun insertActors(list: List<ActorResponse>)
    fun getAllActors(): Flow<List<ActorVo>>
}