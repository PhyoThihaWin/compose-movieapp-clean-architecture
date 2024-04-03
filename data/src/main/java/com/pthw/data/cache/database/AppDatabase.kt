package com.pthw.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pthw.data.cache.database.daos.ActorDao
import com.pthw.data.cache.database.daos.MovieDao
import com.pthw.data.cache.database.entities.ActorEntity
import com.pthw.data.cache.database.entities.MovieEntity
import com.pthw.data.cache.database.typeconverter.IntegerListConverter

/**
 * Created by P.T.H.W on 30/03/2023.
 */
@Database(
    entities = [
        MovieEntity::class,
        ActorEntity::class,
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    IntegerListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
}