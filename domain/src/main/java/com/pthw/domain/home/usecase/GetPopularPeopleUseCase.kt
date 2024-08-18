package com.pthw.domain.home.usecase

import com.pthw.domain.repository.ActorRepository
import com.pthw.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetPopularPeopleUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    operator fun invoke() = actorRepository.getDbPopularPeople()

}