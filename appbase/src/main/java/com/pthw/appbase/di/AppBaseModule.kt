package com.pthw.appbase.di

import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.exceptionmapper.ExceptionHandlerImpl
import com.pthw.appbase.utils.DefaultDispatcherProvider
import com.pthw.domain.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by P.T.H.W on 11/03/2024.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseAppModule {
    @Binds
    abstract fun exceptionMapper(exceptionMapperImpl: ExceptionHandlerImpl): ExceptionHandler

    @Binds
    abstract fun dispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}