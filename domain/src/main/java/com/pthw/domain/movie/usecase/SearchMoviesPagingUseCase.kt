package com.pthw.domain.movie.usecase

import com.pthw.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/08/2024.
 */
class SearchMoviesPagingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(query: String) = movieRepository.searchPagingMovies(query)
}