package com.pthw.domain.movie.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.domain.movie.repository.MovieRepository
import com.pthw.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class GetMovieDetailUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : CoroutineUseCase<String, MovieDetailVo>(dispatcherProvider) {
    override suspend fun provide(params: String): MovieDetailVo {
        return movieRepository.getMovieDetails(params)
    }
}