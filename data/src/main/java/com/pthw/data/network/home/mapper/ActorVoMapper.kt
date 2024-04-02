package com.pthw.data.network.home.mapper

import com.pthw.data.network.home.response.ActorResponse
import com.pthw.domain.model.ActorVo
import com.pthw.shared.extension.orFalse
import com.pthw.shared.extension.orZero
import com.pthw.shared.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class ActorVoMapper @Inject constructor() : UnidirectionalMap<ActorResponse?, ActorVo> {
    override fun map(item: ActorResponse?): ActorVo {
        return ActorVo(
            adult = item?.adult.orFalse(),
            gender = item?.gender.orZero(),
            id = item?.id.orZero(),
            knownForDepartment = item?.knownForDepartment.orEmpty(),
            name = item?.name.orEmpty(),
            originalName = item?.originalName.orEmpty(),
            popularity = item?.popularity.orZero(),
            profilePath = item?.profilePath.orEmpty()
        )
    }
}