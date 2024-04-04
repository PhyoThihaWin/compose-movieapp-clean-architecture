package com.pthw.composemovieappcleanarchitecture.feature.listing

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.domain.model.MovieVo
import com.pthw.domain.usecase.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@HiltViewModel
class MovieListingPageViewModel @Inject constructor(
    private val handler: ExceptionHandler,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
) : ViewModel() {

    var nowPlayingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            runCatching {
                getNowPlayingMoviesUseCase.execute(Unit).collectLatest {
                    nowPlayingMovies.value = ObjViewState.Success(it)
                }
            }.getOrElse {
                Timber.e(it)
                nowPlayingMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    init {
        getNowPlayingMovies()
    }

}

