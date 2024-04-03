package com.pthw.data.cache.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pthw.data.cache.database.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 03/04/2024.
 */
@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movie")
    suspend fun clearMovies()
}