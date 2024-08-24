package com.pthw.composemovieappcleanarchitecture.feature.ticket

import androidx.lifecycle.ViewModel
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.home.usecase.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 22/08/2024.
 */
@HiltViewModel
class TicketNavPageViewModel @Inject constructor(
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
) : ViewModel() {
    val nowPlayingMovies: Flow<List<MovieVo>> = getNowPlayingMoviesUseCase()
}