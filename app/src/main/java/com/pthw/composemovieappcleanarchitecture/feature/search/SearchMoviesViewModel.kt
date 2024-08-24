package com.pthw.composemovieappcleanarchitecture.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pthw.appbase.exceptionmapper.ExceptionHandler
import com.pthw.domain.home.model.MovieVo
import com.pthw.domain.movie.usecase.SearchMoviesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by P.T.H.W on 23/08/2024.
 */
@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val handler: ExceptionHandler,
    val searchMoviesPagingUseCase: SearchMoviesPagingUseCase,
) : ViewModel() {

    val moviesPagingFlow = MutableStateFlow<PagingData<MovieVo>>(PagingData.empty())
    private var searchQuery = MutableStateFlow("")

    init {
        getSearchMovies()
    }

    /**
     * Update search query from UI
     * Handle to prevent continuous request search query
     */
    fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            searchQuery.value = query
        }
    }

    /**
     * Private search function to database after query flow changes
     */
    @OptIn(FlowPreview::class)
    private fun getSearchMovies() {
        viewModelScope.launch {
            searchQuery.debounce(500).collectLatest {
                val data = searchMoviesPagingUseCase(it).cachedIn(viewModelScope)
                moviesPagingFlow.value = data.first()
            }
        }
    }


}