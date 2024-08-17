package com.pthw.domain.home.usecase

import com.pthw.domain.home.repository.HomeRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetUpComingMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() = homeRepository.getDbUpComingMovies()

}