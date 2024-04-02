package com.pthw.appbase.exceptionmapper

import android.content.Context
import com.pthw.appbase.R
import com.pthw.appbase.utils.LocalBroadcastEventBus
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Vincent on 11/27/19
 * Modified by ZMT, PTH.W
 * Changed to KTOR exception mapper...
 */
class ExceptionHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localEventBus: LocalBroadcastEventBus
) : ExceptionHandler {

    companion object {
        private const val ERROR_CODE_400 = 400
        private const val ERROR_CODE_401 = 401
        private const val ERROR_CODE_422 = 422
        private const val ERROR_CODE_403 = 403
        private const val ERROR_CODE_404 = 404
        private const val ERROR_CODE_500 = 500
        private const val ERROR_CODE_503 = 503
        private const val ERROR_CODE_451 = 451
    }

    override fun getCode(item: Throwable): Int {
        return if (item is ClientRequestException) item.response.status.value else 0
    }

    override suspend fun getErrorBody(item: Throwable): String? {
        return if (item is ClientRequestException) item.response.body() else null
    }

    override suspend fun map(item: Throwable): String {
        return when (item) {
            is UnknownHostException -> context.getString(R.string.error_no_internet)
            is SocketTimeoutException -> context.getString(R.string.error_socket_timeout)
            is ConnectException -> context.getString(R.string.error_no_internet)
            is ClientRequestException -> parseNetworkError(item.response)
            else -> item.localizedMessage ?: context.getString(R.string.error_generic)
        }
    }

    private suspend fun parseNetworkError(response: HttpResponse): String {
        when (response.status.value) {
            ERROR_CODE_400 -> return parseMessageFromErrorBody(response.bodyAsText())
                ?: context.getString(R.string.error_generic)

            ERROR_CODE_401 -> {
                /**
                 * As of now, I added 401 handler in authentication interceptor.
                 * Open this comment if we want to handle in ui layer.
                 */
                localEventBus.publish(LocalBroadcastEventBus.EventType.UnAuthenticated)
                Timber.i("send broadcast to inform 401")

                return parseMessageFromErrorBody(response.bodyAsText())
                    ?: context.getString(R.string.error_server_401)
            }

            ERROR_CODE_451 -> {
                /**
                 * Service refuse exception for legal reason. eg: inactive account
                 */
                localEventBus.publish(LocalBroadcastEventBus.EventType.IllegalAccount)
                Timber.i("send broadcast to inform 451")

                return parseMessageFromErrorBody(response.bodyAsText())
                    ?: context.getString(R.string.error_server_451)
            }

            ERROR_CODE_503 -> {
                /**
                 * Under maintenance, endpoints will return 503 status code
                 */
                localEventBus.publish(LocalBroadcastEventBus.EventType.ServiceUnavailable)
                Timber.i("send broadcast to inform 503")

                return parseMessageFromErrorBody(response.bodyAsText())
                    ?: context.getString(R.string.error_server_503)
            }

            ERROR_CODE_422 -> return parseMessageFromErrorBody(response.bodyAsText())
                ?: context.getString(R.string.error_generic)

            ERROR_CODE_403 -> return parseMessageFromErrorBody(response.bodyAsText())
                ?: context.getString(
                    R.string.error_server_403
                )

            ERROR_CODE_404 -> return parseMessageFromErrorBody(response.bodyAsText())
                ?: context.getString(R.string.error_server_404)

            ERROR_CODE_500 -> return parseMessageFromErrorBody(response.bodyAsText())
                ?: context.getString(R.string.error_server_500)
        }

        return parseMessageFromErrorBody(response.bodyAsText())
            ?: context.getString(R.string.error_generic)
    }

    /** Parse error message from error body */
    private fun parseMessageFromErrorBody(errorBody: String): String? {
        return try {
            Timber.e("ErrorBody: $errorBody")
            JSONObject(errorBody).getString("message")
        } catch (exception: Exception) {
            Timber.e(exception)
            null
        }
    }

}



