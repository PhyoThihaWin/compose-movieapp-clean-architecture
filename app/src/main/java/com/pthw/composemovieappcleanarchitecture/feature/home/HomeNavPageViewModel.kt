package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.domain.model.MovieVo
import com.pthw.domain.usecase.GetNowPlayingMoviesUseCase
import com.pthw.domain.usecase.GetUpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@HiltViewModel
class HomeNavPageViewModel @Inject constructor(
    private val handler: ExceptionHandler,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase
) : ViewModel() {

    var nowPlayingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var upComingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set

    private fun getNowPlayingMovies() {
        nowPlayingMovies.value = ObjViewState.Loading()
        viewModelScope.launch {
            runCatching {
                val data = getNowPlayingMoviesUseCase.execute(Unit)
                nowPlayingMovies.value = ObjViewState.Success(data)
            }.getOrElse {
                Timber.e(it)
                nowPlayingMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    private fun getUpComingMovies() {
        upComingMovies.value = ObjViewState.Loading()
        viewModelScope.launch {
            runCatching {
                val data = getUpComingMoviesUseCase.execute(Unit)
                upComingMovies.value = ObjViewState.Success(data)
            }.getOrElse {
                Timber.e(it)
                upComingMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    init {
        getNowPlayingMovies()
        getUpComingMovies()
    }
}