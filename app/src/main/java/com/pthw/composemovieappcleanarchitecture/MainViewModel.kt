package com.pthw.composemovieappcleanarchitecture

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.domain.general.AppThemeMode
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
) : ViewModel() {
    val currentLanguage = mutableStateOf(cacheRepository.getLanguageNormal())
    val appThemeMode = mutableStateOf(cacheRepository.getThemeModeNormal())

    init {
        getLanguageCache()
        getThemeMode()
    }

    private fun getLanguageCache() {
        viewModelScope.launch {
            cacheRepository.getLanguage().collectLatest {
                currentLanguage.value = it
            }
        }
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            cacheRepository.getThemeMode().collectLatest {
                appThemeMode.value = it
            }
        }
    }

}