package com.pthw.domain.home.usecase

import com.pthw.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetUpComingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getDbUpComingMovies()
}