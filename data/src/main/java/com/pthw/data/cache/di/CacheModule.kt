package com.pthw.data.cache.di

import android.content.Context
import androidx.room.Room
import com.pthw.data.cache.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by P.T.H.W on 03/04/2024.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "compose_movie_app.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}