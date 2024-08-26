package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.utils.ResultState
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.usecase.FavoriteMovieUseCase
import com.pthw.domain.home.usecase.FetchHomeDataUseCase
import com.pthw.domain.home.usecase.GetNowPlayingMoviesUseCase
import com.pthw.domain.home.usecase.GetPopularMoviesUseCase
import com.pthw.domain.home.usecase.GetPopularPeopleUseCase
import com.pthw.domain.home.usecase.GetUpComingMoviesUseCase
import com.pthw.domain.movie.usecase.GetMovieGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
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
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
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
        nowPlayingMovies.value = ResultState.Loading
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().onStart { delay(3000) }.collectLatest {
                nowPlayingMovies.value = ResultState.Success(it)
            }
        }
    }

    private fun getUpComingMovies() {
        upComingMovies.value = ResultState.Loading
        viewModelScope.launch {
            getUpComingMoviesUseCase().onStart { delay(3000) }.collectLatest {
                upComingMovies.value = ResultState.Success(it)
            }
        }
    }

    private fun getPopularMovies() {
        popularMovies.value = ResultState.Loading
        viewModelScope.launch {
            getPopularMoviesUseCase().onStart { delay(3000) }.collectLatest {
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
        refreshing.value = true
        viewModelScope.launch {
            delay(2000)
            fetchHomeData()
            refreshing.value = false
        }
    }

    fun favoriteMovie(movieId: Int) {
        viewModelScope.launch {
            favoriteMovieUseCase(movieId)
        }
    }
}