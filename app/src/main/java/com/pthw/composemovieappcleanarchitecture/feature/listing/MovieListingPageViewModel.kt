package com.pthw.composemovieappcleanarchitecture.feature.listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.domain.movie.usecase.GetNowPlayingMoviesPagingUseCase
import com.pthw.domain.movie.usecase.GetUpComingMoviesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@HiltViewModel
class MovieListingPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val handler: ExceptionHandler,
    getNowPlayingMoviesPagingUseCase: GetNowPlayingMoviesPagingUseCase,
    getUpComingMoviesPagingUseCase: GetUpComingMoviesPagingUseCase
) : ViewModel() {

    val movieType: String? = savedStateHandle["movieType"]

    fun getMovieListingFlow() =
        if (movieType == MovieListingPageNavigation.NOW_PLAYING) nowPlayingPagingFlow else upComingPagingFlow

    val nowPlayingPagingFlow =
        getNowPlayingMoviesPagingUseCase.execute(Unit).cachedIn(viewModelScope)
    val upComingPagingFlow = getUpComingMoviesPagingUseCase.execute(Unit).cachedIn(viewModelScope)

}

