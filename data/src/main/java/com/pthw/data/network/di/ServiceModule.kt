package com.pthw.data.network.di

import com.pthw.data.network.feature.home.HomeService
import com.pthw.data.network.feature.movie.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.HttpClient

/**
 * Created by P.T.H.W on 11/03/2024.
 */

@Module
@InstallIn(ViewModelComponent::class)
object MainServiceModule {

    @Provides
    fun provideHomeService(ktor: HttpClient): HomeService {
        return HomeService(ktor)
    }

    @Provides
    fun provideMovieService(ktor: HttpClient): MovieService {
        return MovieService(ktor)
    }

}