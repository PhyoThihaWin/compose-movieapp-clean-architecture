package com.pthw.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Entity(tableName = "actor")
data class ActorEntity(
    @PrimaryKey(autoGenerate = true) val tableId: Long = 0L,
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)
