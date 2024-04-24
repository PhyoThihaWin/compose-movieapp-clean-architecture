package com.pthw.data.network.feature.movie.mapper

import com.pthw.data.network.feature.movie.response.MovieDetailCreditsResponse
import com.pthw.data.network.ktor.IMAGE_BASE_URL
import com.pthw.domain.movie.model.MovieCastVo
import com.pthw.shared.extension.orFalse
import com.pthw.shared.extension.orZero
import com.pthw.shared.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class MovieCastVoMapper @Inject constructor() :
    UnidirectionalMap<MovieDetailCreditsResponse.Cast?, MovieCastVo> {
    override fun map(item: MovieDetailCreditsResponse.Cast?): MovieCastVo {
        return MovieCastVo(
            adult = item?.adult.orFalse(),
            gender = item?.gender.orZero(),
            id = item?.id.orZero(),
            knownForDepartment = item?.knownForDepartment.orEmpty(),
            name = item?.name.orEmpty(),
            originalName = item?.originalName.orEmpty(),
            popularity = item?.popularity.orZero(),
            profilePath = IMAGE_BASE_URL + item?.profilePath.orEmpty(),
            castId = item?.castId.orZero(),
            character = item?.character.orEmpty(),
            creditId = item?.creditId.orEmpty(),
            order = item?.order.orZero()
        )
    }
}