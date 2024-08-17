package com.pthw.domain.movie.usecase

import com.pthw.domain.movie.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/04/2024.
 */
class GetGenreByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int) = movieRepository.getGenreById(id)
}