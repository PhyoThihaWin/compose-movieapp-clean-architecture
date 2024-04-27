package com.pthw.domain.movie.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.movie.model.GenreVo
import com.pthw.domain.movie.repository.MovieRepository
import com.pthw.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/04/2024.
 */
class GetGenreByIdUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : CoroutineUseCase<Int, GenreVo>(dispatcherProvider) {
    override suspend fun provide(params: Int): GenreVo {
        return movieRepository.getGenreById(params)
    }
}