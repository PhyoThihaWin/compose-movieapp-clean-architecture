package com.pthw.domain.movie.usecase

import com.pthw.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(params: String) = movieRepository.getMovieDetails(params)
}