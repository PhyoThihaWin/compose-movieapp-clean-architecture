package com.pthw.appbase.exceptionmapper

import com.pthw.shared.mapper.UnidirectionalSuspendMap


interface ExceptionHandler : UnidirectionalSuspendMap<Throwable, String> {
    fun getCode(item: Throwable): Int
    suspend fun getErrorBody(item: Throwable): String?
}
