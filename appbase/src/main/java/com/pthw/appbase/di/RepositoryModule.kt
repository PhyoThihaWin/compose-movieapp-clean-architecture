package com.pthw.appbase.di

import com.pthw.data.repository.HomeRepositoryImpl
import com.pthw.data.repository.MovieRepositoryImpl
import com.pthw.domain.home.repository.HomeRepository
import com.pthw.domain.movie.repository.MovieRepository
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
    abstract fun bindHomeRepository(repositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository
}