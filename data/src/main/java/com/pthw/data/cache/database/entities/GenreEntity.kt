package com.pthw.data.cache.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by P.T.H.W on 26/04/2024.
 */

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)
