package com.pthw.domain.movie.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.repository.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(params: String) = withContext(dispatcherProvider.io()) {
        movieRepository.getMovieDetails(params)
    }
}