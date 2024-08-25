package com.pthw.appbase.di

import com.pthw.data.local.repository.CacheRepositoryImpl
import com.pthw.data.repository.ActorRepositoryImpl
import com.pthw.data.repository.MovieRepositoryImpl
import com.pthw.domain.repository.ActorRepository
import com.pthw.domain.repository.CacheRepository
import com.pthw.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindActorRepository(repository: ActorRepositoryImpl): ActorRepository

    @Binds
    abstract fun bindCacheRepository(repository: CacheRepositoryImpl): CacheRepository
}