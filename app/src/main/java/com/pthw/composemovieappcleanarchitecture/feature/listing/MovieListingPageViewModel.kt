package com.pthw.composemovieappcleanarchitecture.feature.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.domain.movie.usecase.GetNowPlayingMoviesPagingUseCase
import com.pthw.domain.movie.usecase.GetUpComingMoviesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@HiltViewModel
class MovieListingPageViewModel @Inject constructor(
    private val handler: ExceptionHandler,
    private val getNowPlayingMoviesPagingUseCase: GetNowPlayingMoviesPagingUseCase,
    private val getUpComingMoviesPagingUseCase: GetUpComingMoviesPagingUseCase
) : ViewModel() {

    val nowPlayingPagingFlow get() = getNowPlayingMoviesPagingUseCase.execute(Unit).cachedIn(viewModelScope)
    val upComingPagingFlow get() = getUpComingMoviesPagingUseCase.execute(Unit).cachedIn(viewModelScope)

}

