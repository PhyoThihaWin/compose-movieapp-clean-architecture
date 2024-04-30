package com.pthw.domain.movie.usecase

import androidx.paging.PagingData
import com.pthw.domain.DispatcherProvider
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.repository.MovieRepository
import com.pthw.domain.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */
class GetUpComingMoviesPagingUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : FlowUseCase<Unit, PagingData<MovieVo>>(dispatcherProvider) {
    override fun provide(params: Unit): Flow<PagingData<MovieVo>> {
        return movieRepository.getUpComingMovies()
    }
}