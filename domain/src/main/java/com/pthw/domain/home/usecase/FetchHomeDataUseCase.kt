package com.pthw.domain.home.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.home.repository.HomeRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by P.T.H.W on 20/04/2024.
 */
class FetchHomeDataUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke() = withContext(dispatcherProvider.io()) {
        homeRepository.fetchHomeData()
    }
}