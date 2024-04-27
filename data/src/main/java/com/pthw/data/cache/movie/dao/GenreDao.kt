package com.pthw.data.cache.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pthw.data.cache.database.entities.ActorEntity
import com.pthw.data.cache.database.entities.GenreEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 26/04/2024.
 */

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(list: List<GenreEntity>)

    @Query("SELECT * FROM genre WHERE id=:id")
    fun getGenreById(id: Int): GenreEntity

    @Query("SELECT * FROM genre")
    fun getAllGenres(): List<GenreEntity>

    @Query("DELETE FROM genre")
    suspend fun clearGenres()
}