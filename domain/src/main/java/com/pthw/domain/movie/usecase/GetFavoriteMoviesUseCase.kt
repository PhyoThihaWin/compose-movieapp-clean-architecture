package com.pthw.domain.movie.usecase

import com.pthw.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 27/08/2024.
 */
class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    operator fun invoke() = repository.getDbFavoriteMovies()
}