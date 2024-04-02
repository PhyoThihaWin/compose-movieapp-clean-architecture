package com.pthw.domain.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.model.MovieVo
import com.pthw.domain.repository.HomeRepository
import com.pthw.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetNowPlayingMoviesUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository
) : CoroutineUseCase<Unit, List<MovieVo>>(dispatcherProvider) {
    override suspend fun provide(params: Unit): List<MovieVo> {
        return homeRepository.getNowPlayingMovies()
    }
}