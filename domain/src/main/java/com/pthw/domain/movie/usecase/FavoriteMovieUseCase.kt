package com.pthw.domain.movie.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.repository.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/08/2024.
 */
class FavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(movieId: Int) = withContext(dispatcherProvider.io()) {
        movieRepository.favoriteDbMovie(movieId)
    }
}