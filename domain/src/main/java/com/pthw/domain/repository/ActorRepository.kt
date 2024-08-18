package com.pthw.domain.repository

import com.pthw.domain.home.model.ActorVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 18/08/2024.
 */
interface ActorRepository {
    suspend fun fetchPopularPeople()
    fun getDbPopularPeople(): Flow<List<ActorVo>>
}