package com.pthw.domain.utils

import com.pthw.domain.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Vincent on 2019-10-21
 */

abstract class FlowUseCase<I, O>(
    protected val dispatcherProvider: DispatcherProvider
) {

    fun execute(params: I): Flow<O> {
        return provide(params)
            .flowOn(dispatcherProvider.io())
    }

    protected abstract fun provide(
        params: I
    ): Flow<O>

}
