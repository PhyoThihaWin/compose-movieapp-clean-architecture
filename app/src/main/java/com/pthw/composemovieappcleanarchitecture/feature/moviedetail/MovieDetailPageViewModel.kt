package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.utils.ResultState
import com.pthw.composemovieappcleanarchitecture.navigation.Routes
import com.pthw.domain.movie.usecase.FavoriteMovieUseCase
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.domain.movie.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 08/04/2024.
 */

@HiltViewModel
class MovieDetailPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val handler: ExceptionHandler,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {

    val navArgs = savedStateHandle.toRoute<Routes.MovieDetail>()

    var movieDetails = mutableStateOf<ResultState<MovieDetailVo>>(ResultState.Idle)
        private set

    init {
        getMovieDetails(navArgs.id)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieDetails.value = ResultState.Loading
            runCatching {
                delay(400)
                val details = getMovieDetailUseCase(movieId)
                movieDetails.value = ResultState.Success(details)
            }.getOrElse {
                Timber.e(it)
                movieDetails.value = ResultState.Error(handler.map(it))
            }
        }
    }

    fun favoriteMovie(movieId: Int) {
        viewModelScope.launch {
            val data = movieDetails.value
            if (data is ResultState.Success) {
                movieDetails.value =
                    ResultState.Success(data.value.copy(isFavorite = !data.value.isFavorite))
            }

            favoriteMovieUseCase(movieId)
        }
    }

}