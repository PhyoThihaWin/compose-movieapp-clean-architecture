package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.home.usecase.GetNowPlayingMoviesUseCase
import com.pthw.domain.home.usecase.GetPopularMoviesUseCase
import com.pthw.domain.home.usecase.GetPopularPeopleUseCase
import com.pthw.domain.home.usecase.GetUpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    var refreshing = mutableStateOf(false)
        private set
    var nowPlayingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var upComingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var popularMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var popularPeople = mutableStateOf<ObjViewState<List<ActorVo>>>(ObjViewState.Idle())
        private set

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            runCatching {
                getNowPlayingMoviesUseCase.execute(Unit).collectLatest {
                    nowPlayingMovies.value = ObjViewState.Success(it)
                    refreshing.value = false
                }
            }.getOrElse {
                Timber.e(it)
                nowPlayingMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            runCatching {
                getUpComingMoviesUseCase.execute(Unit).collectLatest {
                    upComingMovies.value = ObjViewState.Success(it)
                }
            }.getOrElse {
                Timber.e(it)
                upComingMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            runCatching {
                getPopularMoviesUseCase.execute(Unit).collectLatest {
                    popularMovies.value = ObjViewState.Success(it)
                }
            }.getOrElse {
                Timber.e(it)
                popularMovies.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    private fun getPopularPeople() {
        viewModelScope.launch {
            runCatching {
                getPopularPeopleUseCase.execute(Unit).collectLatest {
                    popularPeople.value = ObjViewState.Success(it)
                }
            }.getOrElse {
                Timber.e(it)
                popularPeople.value = ObjViewState.Error(handler.getErrorBody(it).orEmpty())
            }
        }
    }

    private fun fetchHomeData() {
        getNowPlayingMovies()
        getUpComingMovies()
        getPopularMovies()
        getPopularPeople()
    }

    fun refreshHomeData() = viewModelScope.launch {
        refreshing.value = true
        delay(1000)
        fetchHomeData()
    }

    init {
        fetchHomeData()
    }
}