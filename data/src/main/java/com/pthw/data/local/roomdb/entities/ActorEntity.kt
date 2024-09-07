package com.pthw.data.local.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.domain.home.model.ActorVo

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
) {
    fun toActorVo() = ActorVo(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = IMAGE_BASE_URL + profilePath
    )
}
