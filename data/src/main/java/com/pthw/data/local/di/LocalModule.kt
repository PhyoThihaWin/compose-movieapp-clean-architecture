package com.pthw.data.local.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.pthw.data.local.datasource.ActorDataSource
import com.pthw.data.local.datasource.GenreDataSource
import com.pthw.data.local.datasource.MovieDataSource
import com.pthw.data.local.realmdb.datasource_impl.ActorRealmDataSourceImpl
import com.pthw.data.local.realmdb.datasource_impl.GenreRealmDataSourceImpl
import com.pthw.data.local.realmdb.datasource_impl.MovieRealmDataSourceImpl
import com.pthw.data.local.roomdb.AppDatabase
import com.pthw.data.local.realmdb.RealmDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
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

    @Provides
    @Singleton
    fun provideRealmDatabase(): Realm {
        return RealmDatabase
    }

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context) = context.dataStore

    private val Context.dataStore by preferencesDataStore("pref.foodDi")

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun provideMovieDataSource(dataSource: MovieRealmDataSourceImpl): MovieDataSource

    @Binds
    abstract fun provideActorDataSource(dataSource: ActorRealmDataSourceImpl): ActorDataSource

    @Binds
    abstract fun provideGenreDataSource(dataSource: GenreRealmDataSourceImpl): GenreDataSource
}