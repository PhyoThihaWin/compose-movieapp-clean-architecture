package com.pthw.data.local.realmdb.entity

import com.pthw.domain.home.model.ActorVo
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * Created by P.T.H.W on 02/04/2024.
 */

class ActorRealmEntity : RealmObject {
    @PrimaryKey
    var tableId: ObjectId = ObjectId()
    var adult: Boolean = false
    var gender: Int = 0
    var id: Int = 0
    var knownForDepartment: String = ""
    var name: String = ""
    var originalName: String = ""
    var popularity: Double = 0.0
    var profilePath: String = ""

    fun toActorVo() = ActorVo(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath
    )
}