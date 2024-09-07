package com.pthw.data.local.realmdb.entity

import com.pthw.domain.movie.model.GenreVo
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * Created by P.T.H.W on 26/04/2024.
 */

class GenreRealmEntity : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""

    fun toGenreVo() = GenreVo(
        id = id,
        name = name
    )
}
