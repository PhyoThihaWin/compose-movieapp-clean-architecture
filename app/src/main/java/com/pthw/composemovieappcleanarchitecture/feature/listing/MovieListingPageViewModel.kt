package com.pthw.composemovieappcleanarchitecture.feature.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.domain.movie.usecase.GetNowPlayingMoviesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@HiltViewModel
class MovieListingPageViewModel @Inject constructor(
    private val handler: ExceptionHandler,
    private val getNowPlayingMoviesPagingUseCase: GetNowPlayingMoviesPagingUseCase
) : ViewModel() {

//    var nowPlayingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
//        private set
//
//    private fun getNowPlayingMovies() {
//        viewModelScope.launch {
//            runCatching {
//                getNowPlayingMoviesUseCase.execute(Unit).collectLatest {
//                    nowPlayingMovies.value = ObjViewState.Success(it)
//                }
//            }.getOrElse {
//                Timber.e(it)
//                nowPlayingMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
//            }
//        }
//    }
//
//    init {
//        getNowPlayingMovies()
//    }

    val pagingFlow get() = getNowPlayingMoviesPagingUseCase.execute(Unit).cachedIn(viewModelScope)

}

