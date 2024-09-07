package com.pthw.data.local.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pthw.domain.movie.model.GenreVo

/**
 * Created by P.T.H.W on 26/04/2024.
 */

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
) {
    fun toGenreVo() = GenreVo(
        id = id,
        name = name
    )
}
