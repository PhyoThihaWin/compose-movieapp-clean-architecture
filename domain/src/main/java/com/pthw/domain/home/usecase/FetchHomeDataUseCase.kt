package com.pthw.domain.home.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.home.repository.HomeRepository
import com.pthw.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 20/04/2024.
 */
class FetchHomeDataUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository,
) : CoroutineUseCase<Unit, Unit>(dispatcherProvider) {
    override suspend fun provide(params: Unit) {
        homeRepository.fetchHomeData()
    }
}