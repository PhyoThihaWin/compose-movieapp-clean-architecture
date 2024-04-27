package com.pthw.domain.movie.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.movie.repository.MovieRepository
import com.pthw.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/04/2024.
 */
class GetMovieGenresUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : CoroutineUseCase<Unit, Unit>(dispatcherProvider) {
    override suspend fun provide(params: Unit) {
        movieRepository.getMovieGenres()
    }
}