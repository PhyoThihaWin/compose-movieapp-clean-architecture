package com.pthw.shared.mapper

/**
 * Created by Vincent on 2019-06-16
 */
interface UnidirectionalSuspendMap<F, T> {

    suspend fun map(item: F): T
}

