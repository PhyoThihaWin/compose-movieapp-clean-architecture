package com.pthw.data.network.feature.home.mapper

import com.pthw.data.network.feature.home.response.ActorResponse
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.domain.home.model.ActorVo
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
            profilePath = IMAGE_BASE_URL + item?.profilePath.orEmpty()
        )
    }
}