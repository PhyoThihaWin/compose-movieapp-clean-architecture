package com.pthw.composemovieappcleanarchitecture.feature.profile

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
class ProfileNavPageViewModel @Inject constructor(
    private val cacheRepository: CacheRepository
) : ViewModel() {
    //    val appThemeMode = mutableStateOf(cacheRepository.getThemeModeNormal())
    val currentLanguage = mutableStateOf(cacheRepository.getLanguageNormal())

    init {
        getLanguageCache()
    }

    /**
     * Update app chose language to PreferenceDataStore
     */
    fun updateLanguageCache(localeCode: String) {
        viewModelScope.launch {
            cacheRepository.putLanguage(localeCode)
        }
    }

    private fun getLanguageCache() {
        viewModelScope.launch {
            cacheRepository.getLanguage().collectLatest {
                currentLanguage.value = it
            }
        }
    }
}