package com.pthw.composemovieappcleanarchitecture.feature.favorite

import androidx.lifecycle.ViewModel
import com.pthw.domain.movie.usecase.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by P.T.H.W on 22/08/2024.
 */
@HiltViewModel
class FavoriteNavPageViewModel @Inject constructor(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {
    val movies = getFavoriteMoviesUseCase()
}