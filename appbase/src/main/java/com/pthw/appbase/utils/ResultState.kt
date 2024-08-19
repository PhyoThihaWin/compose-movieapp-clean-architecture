package com.pthw.appbase.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * Created by P.T.H.W on 18/08/2024.
 */
@Stable
sealed class ResultState<out T> {
    data object Idle : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data class Success<out T>(val value: T) : ResultState<T>()
    data class Error<out T>(val errorMessage: String) : ResultState<T>()
}

@Composable
fun <T> ResultRender(
    state: ResultState<T>,
    loading: @Composable () -> Unit = {},
    error: @Composable (msg: String) -> Unit = {},
    success: @Composable (data: T) -> Unit,
) {
    when (state) {
        is ResultState.Idle -> {}

        is ResultState.Loading -> {
            loading.invoke()
        }

        is ResultState.Success -> {
            success.invoke(state.value)
        }

        is ResultState.Error -> {
            error.invoke(state.errorMessage)
        }
    }
}