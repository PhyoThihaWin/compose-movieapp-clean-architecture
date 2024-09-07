package com.pthw.data.local.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pthw.data.local.roomdb.dao.ActorDao
import com.pthw.data.local.roomdb.dao.MovieDao
import com.pthw.data.local.roomdb.entities.ActorEntity
import com.pthw.data.local.roomdb.entities.GenreEntity
import com.pthw.data.local.roomdb.entities.MovieEntity
import com.pthw.data.local.roomdb.typeconverter.IntegerListConverter
import com.pthw.data.local.roomdb.typeconverter.StringListConverter
import com.pthw.data.local.roomdb.dao.GenreDao

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