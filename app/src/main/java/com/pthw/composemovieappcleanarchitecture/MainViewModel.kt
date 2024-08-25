package com.pthw.composemovieappcleanarchitecture

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.domain.repository.CacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by P.T.H.W on 25/08/2024.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheRepository: CacheRepository
): ViewModel() {
    val currentLanguage = mutableStateOf(cacheRepository.getLanguageNormal())

    init {
        getLanguageCache()
    }

    private fun getLanguageCache() {
        viewModelScope.launch {
            cacheRepository.getLanguage().collectLatest {
                currentLanguage.value = it
            }
        }
    }
}