package com.pthw.domain.home.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.repository.ActorRepository
import com.pthw.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by P.T.H.W on 20/04/2024.
 */
class FetchHomeDataUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository,
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke() = withContext(dispatcherProvider.io()) {
        coroutineScope {
            async { movieRepository.fetchNowPlayingMovies() }.await()
            async { movieRepository.fetchUpComingMovies() }.await()
            async { movieRepository.fetchPopularMovies() }.await()
            async { actorRepository.fetchPopularPeople() }.await()
        }
    }
}