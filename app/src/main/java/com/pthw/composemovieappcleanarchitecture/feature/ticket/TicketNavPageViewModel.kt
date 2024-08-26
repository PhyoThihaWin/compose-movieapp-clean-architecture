package com.pthw.composemovieappcleanarchitecture.feature.ticket

import androidx.lifecycle.ViewModel
import com.pthw.domain.movie.usecase.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by P.T.H.W on 22/08/2024.
 */
@HiltViewModel
class TicketNavPageViewModel @Inject constructor(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {
    val movies = getFavoriteMoviesUseCase()
}