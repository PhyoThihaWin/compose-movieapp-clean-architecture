package com.pthw.domain.movie.usecase

import com.pthw.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */
class GetNowPlayingMoviesPagingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getNowPlayingPagingMovies()
}