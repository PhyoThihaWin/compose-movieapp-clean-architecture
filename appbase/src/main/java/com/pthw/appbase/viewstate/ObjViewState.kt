package com.pthw.appbase.viewstate

import androidx.compose.runtime.Composable

sealed class ObjViewState<out T> {
    open operator fun invoke(): T? = null

    class Idle<out T> : ObjViewState<T>()
    class Loading<out T> : ObjViewState<T>()
    data class Success<out T>(val value: T) : ObjViewState<T>()
    data class Error<out T>(val errorMessage: String) : ObjViewState<T>()
}

/**
 * For normal state parsing
 */
fun <T> ObjViewState<T>.renderState(
    idle: () -> Unit = {},
    loading: () -> Unit = {},
    success: (data: T) -> Unit,
    error: (msg: String) -> Unit = {},
) {
    when (this) {
        is ObjViewState.Loading -> {
            loading.invoke()
        }

        is ObjViewState.Success -> {
            success.invoke(this.value)
        }

        is ObjViewState.Error -> {
            error.invoke(this.errorMessage)
        }

        else -> idle.invoke()
    }
}

/**
 * For jetpack compose state parsing
 */
@Composable
fun <T> RenderCompose(
    state: ObjViewState<T>,
    idle: @Composable () -> Unit = {},
    loading: @Composable () -> Unit = {},
    success: @Composable (data: T) -> Unit,
    error: @Composable (msg: String) -> Unit = {},
) {
    when (state) {
        is ObjViewState.Loading -> {
            loading.invoke()
        }

        is ObjViewState.Success -> {
            success.invoke(state.value)
        }

        is ObjViewState.Error -> {
            error.invoke(state.errorMessage)
        }

        else -> idle.invoke()
    }
}