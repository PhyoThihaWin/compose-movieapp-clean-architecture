package com.pthw.domain.home.usecase

import com.pthw.domain.DispatcherProvider
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.home.repository.HomeRepository
import com.pthw.domain.utils.CoroutineUseCase
import com.pthw.domain.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetPopularPeopleUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository
) : FlowUseCase<Unit, List<ActorVo>>(dispatcherProvider) {
    override fun provide(params: Unit): Flow<List<ActorVo>> {
        return homeRepository.getDbPopularPeople()
    }

}