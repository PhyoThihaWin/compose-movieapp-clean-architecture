package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.utils.ResultState
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.domain.movie.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    val movieVo: MovieVo = savedStateHandle.toRoute<MovieVo>()

    var movieDetails = mutableStateOf<ResultState<MovieDetailVo>>(ResultState.Idle)
        private set

    init {
        getMovieDetails(movieVo.id.toString())
    }

    private fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            movieDetails.value = ResultState.Loading
            runCatching {
                val details = getMovieDetailUseCase(movieId)
                movieDetails.value = ResultState.Success(details)
            }.getOrElse {
                Timber.e(it)
                movieDetails.value = ResultState.Error(handler.map(it))
            }
        }
    }

}