package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.home.usecase.FetchHomeDataUseCase
import com.pthw.domain.home.usecase.GetNowPlayingMoviesUseCase
import com.pthw.domain.home.usecase.GetPopularMoviesUseCase
import com.pthw.domain.home.usecase.GetPopularPeopleUseCase
import com.pthw.domain.home.usecase.GetUpComingMoviesUseCase
import com.pthw.domain.movie.usecase.GetMovieGenresUseCase
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
    private val fetchHomeDataUseCase: FetchHomeDataUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase
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


    init {
        // from database
        getNowPlayingMovies()
        getUpComingMovies()
        getPopularMovies()
        getPopularPeople()

        // from network
        fetchHomeData()
        fetchMovieGenres()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            runCatching {
                fetchHomeDataUseCase.execute(Unit)
            }.getOrElse {
                Timber.e(it)
            }
        }
    }

    private fun fetchMovieGenres() {
        viewModelScope.launch {
            runCatching {
                getMovieGenresUseCase.execute(Unit)
            }.getOrElse {
                Timber.e(it)
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase.execute(Unit).collectLatest {
                if (it.isNotEmpty()) {
                    nowPlayingMovies.value = ObjViewState.Success(it)
                }
            }
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            getUpComingMoviesUseCase.execute(Unit).collectLatest {
                upComingMovies.value = ObjViewState.Success(it)
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.execute(Unit).collectLatest {
                popularMovies.value = ObjViewState.Success(it)
            }
        }
    }

    private fun getPopularPeople() {
        viewModelScope.launch {
            getPopularPeopleUseCase.execute(Unit).collectLatest {
                popularPeople.value = ObjViewState.Success(it)
            }
        }
    }

    fun refreshHomeData() {
        viewModelScope.launch {
            refreshing.value = true
            delay(1000)
            fetchHomeData()
            refreshing.value = false
        }
    }
}