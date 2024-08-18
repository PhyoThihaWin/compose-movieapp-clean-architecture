package com.pthw.data.local.actor

import com.pthw.data.local.database.entities.ActorEntity
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.domain.home.model.ActorVo
import com.pthw.shared.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class ActorEntityVoMapper @Inject constructor() : UnidirectionalMap<ActorEntity, ActorVo> {
    override fun map(item: ActorEntity): ActorVo {
        return ActorVo(
            adult = item.adult,
            gender = item.gender,
            id = item.id,
            knownForDepartment = item.knownForDepartment,
            name = item.name,
            originalName = item.originalName,
            popularity = item.popularity,
            profilePath = IMAGE_BASE_URL + item.profilePath
        )
    }
}