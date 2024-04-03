package com.pthw.data.cache.home.daos

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


    @Query("SELECT * FROM movie where isNowPlaying =:isNowPlaying and isUpComing = :isUpComing and isPopular = :isPopular")
    fun getHomeMovies(
        isNowPlaying: Boolean = false,
        isUpComing: Boolean = false,
        isPopular: Boolean = false
    ): Flow<List<MovieEntity>>

    @Query("DELETE FROM movie where isNowPlaying = 1")
    suspend fun deleteNowPlayingMovies()

    @Query("DELETE FROM movie where isUpComing = 1")
    suspend fun deleteUpComingMovies()

    @Query("DELETE FROM movie where isPopular = 1")
    suspend fun deletePopularMovies()
}