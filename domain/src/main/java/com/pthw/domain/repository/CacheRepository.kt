package com.pthw.domain.repository

import kotlinx.coroutines.flow.Flow

interface CacheRepository {
    fun getLanguageNormal(): String
    fun getLanguage(): Flow<String>
    suspend fun putLanguage(localeCode: String)

//    fun getThemeModeNormal(): String
//    fun getThemeMode(): Flow<String>
//    suspend fun putThemeMode(theme: String)
}