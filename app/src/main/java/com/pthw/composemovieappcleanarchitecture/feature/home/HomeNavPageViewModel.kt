package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.utils.ResultState
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
    var nowPlayingMovies = mutableStateOf<ResultState<List<MovieVo>>>(ResultState.Idle)
        private set
    var upComingMovies = mutableStateOf<ResultState<List<MovieVo>>>(ResultState.Idle)
        private set
    var popularMovies = mutableStateOf<ResultState<List<MovieVo>>>(ResultState.Idle)
        private set
    var popularPeople = mutableStateOf<ResultState<List<ActorVo>>>(ResultState.Idle)
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

        val gg = ResultState.Success<String>("")
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            runCatching {
                fetchHomeDataUseCase()
            }.getOrElse {
                Timber.e(it)
            }
        }
    }

    private fun fetchMovieGenres() {
        viewModelScope.launch {
            runCatching {
                getMovieGenresUseCase()
            }.getOrElse {
                Timber.e(it)
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().collectLatest {
                if (it.isNotEmpty()) {
                    delay(200)
                    nowPlayingMovies.value = ResultState.Success(it)
                }
            }
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            getUpComingMoviesUseCase().collectLatest {
                upComingMovies.value = ResultState.Success(it)
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collectLatest {
                popularMovies.value = ResultState.Success(it)
            }
        }
    }

    private fun getPopularPeople() {
        viewModelScope.launch {
            getPopularPeopleUseCase().collectLatest {
                popularPeople.value = ResultState.Success(it)
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