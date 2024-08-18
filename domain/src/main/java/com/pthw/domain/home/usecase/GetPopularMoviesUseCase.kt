package com.pthw.domain.home.usecase

import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<MovieVo>> = movieRepository.getDbPopularMovies()
}