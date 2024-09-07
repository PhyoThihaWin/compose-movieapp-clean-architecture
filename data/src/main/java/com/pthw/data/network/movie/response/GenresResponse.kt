package com.pthw.data.network.movie.response


import com.pthw.data.local.roomdb.entities.GenreEntity
import com.pthw.data.local.realmdb.entity.GenreRealmEntity
import com.pthw.shared.extension.orZero
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres") val genres: List<Genre?>?
) {
    @Serializable
    data class Genre(
        @SerialName("id") val id: Int?,
        @SerialName("name") val name: String?
    )
}

fun GenresResponse.Genre?.toGenreEntity(): GenreEntity = GenreEntity(
    id = this?.id.orZero(),
    name = this?.name.orEmpty()
)

fun GenresResponse.Genre?.toGenreRealEntity(): GenreRealmEntity = GenreRealmEntity().apply {
    id = this@toGenreRealEntity?.id.orZero()
    name = this@toGenreRealEntity?.name.orEmpty()
}