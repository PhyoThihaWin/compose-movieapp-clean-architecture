package com.pthw.domain.utils

import com.pthw.domain.DispatcherProvider
import kotlinx.coroutines.withContext

/**
 * Created by Vincent on 2019-10-21
 */

abstract class CoroutineUseCase<I, O>(private val dispatcherProvider: DispatcherProvider) {
    suspend fun execute(params: I): O {
        return withContext(dispatcherProvider.io()) {
            provide(params)
        }
    }

    protected abstract suspend fun provide(params: I): O
}

data class TwoParams<T1, T2>(
    val one: T1,
    val two: T2
)

data class ThreeParams<T1, T2, T3>(
    val one: T1,
    val two: T2,
    val three: T3
)

data class FourParams<T1, T2, T3, T4>(
    val one: T1,
    val two: T2,
    val three: T3,
    val four: T4
)