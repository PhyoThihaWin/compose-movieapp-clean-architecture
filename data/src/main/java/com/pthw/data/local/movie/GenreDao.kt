package com.pthw.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pthw.data.local.database.entities.GenreEntity

/**
 * Created by P.T.H.W on 26/04/2024.
 */

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(list: List<GenreEntity>)

    @Query("SELECT * FROM genre WHERE id=:id")
    suspend fun getGenreById(id: Int): GenreEntity

    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("DELETE FROM genre")
    suspend fun clearGenres()
}