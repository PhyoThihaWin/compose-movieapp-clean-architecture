package com.pthw.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pthw.data.cache.home.daos.ActorDao
import com.pthw.data.cache.home.daos.MovieDao
import com.pthw.data.cache.database.entities.ActorEntity
import com.pthw.data.cache.database.entities.GenreEntity
import com.pthw.data.cache.database.entities.MovieEntity
import com.pthw.data.cache.database.typeconverter.IntegerListConverter
import com.pthw.data.cache.database.typeconverter.StringListConverter
import com.pthw.data.cache.movie.dao.GenreDao

/**
 * Created by P.T.H.W on 30/03/2023.
 */
@Database(
    entities = [
        MovieEntity::class,
        ActorEntity::class,
        GenreEntity::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(
    IntegerListConverter::class,
    StringListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun genreDao(): GenreDao
}