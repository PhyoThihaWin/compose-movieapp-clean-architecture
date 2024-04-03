package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.domain.model.ActorVo
import com.pthw.domain.model.MovieVo
import com.pthw.domain.usecase.GetNowPlayingMoviesUseCase
import com.pthw.domain.usecase.GetPopularMoviesUseCase
import com.pthw.domain.usecase.GetPopularPeopleUseCase
import com.pthw.domain.usecase.GetUpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase
) : ViewModel() {

    var nowPlayingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var upComingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var popularMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var popularPeople = mutableStateOf<ObjViewState<List<ActorVo>>>(ObjViewState.Idle())
        private set

    private fun getNowPlayingMovies() {
        nowPlayingMovies.value = ObjViewState.Loading()
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

    private fun getPopularMovies() {
        popularMovies.value = ObjViewState.Loading()
        viewModelScope.launch {
            runCatching {
                val data = getPopularMoviesUseCase.execute(Unit)
                popularMovies.value = ObjViewState.Success(data)
            }.getOrElse {
                Timber.e(it)
                popularMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    private fun getPopularPeople() {
        popularPeople.value = ObjViewState.Loading()
        viewModelScope.launch {
            runCatching {
                val data = getPopularPeopleUseCase.execute(Unit)
                popularPeople.value = ObjViewState.Success(data)
            }.getOrElse {
                Timber.e(it)
                popularPeople.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    init {
        getNowPlayingMovies()
        getUpComingMovies()
        getPopularMovies()
        getPopularPeople()
    }
}