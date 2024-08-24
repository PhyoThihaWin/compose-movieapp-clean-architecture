package com.pthw.domain.movie.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.repository.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/04/2024.
 */
class GetMovieGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke() = withContext(dispatcherProvider.io()) {
        movieRepository.getMovieGenres()
    }
}