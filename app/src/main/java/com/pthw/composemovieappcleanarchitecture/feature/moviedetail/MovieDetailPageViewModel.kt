package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.appbase.viewstate.ObjViewState
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


    var movieDetails = mutableStateOf<ObjViewState<MovieDetailVo>>(ObjViewState.Idle())
        private set

    init {
        val movieId: String = checkNotNull(savedStateHandle["movieId"])
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            movieDetails.value = ObjViewState.Loading()
            runCatching {
                val details = getMovieDetailUseCase.execute(movieId)
                movieDetails.value = ObjViewState.Success(details)
            }.getOrElse {
                Timber.e(it)
                movieDetails.value = ObjViewState.Error(handler.map(it))
            }
        }
    }

}