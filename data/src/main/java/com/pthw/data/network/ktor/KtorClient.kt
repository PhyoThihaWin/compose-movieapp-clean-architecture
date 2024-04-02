package com.pthw.data.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 15L
private const val READ_TIMEOUT = 60L
private const val WRITE_TIMEOUT = 15L

// ktor http client
@OptIn(ExperimentalSerializationApi::class)
val ktorHttpClient: (List<Interceptor>) -> HttpClient = { interceptors ->
    HttpClient(OkHttp) {
        expectSuccess = true

        // okhttp engine config
        engine {
            config {
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

                interceptors.forEach {
                    addInterceptor(it)
                }
            }
        }

        // kotlinx serialization
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        // ktor logger
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) = Timber.tag("Ktor logger:").v(message)
            }
        }

        // ktor response observer
        install(ResponseObserver) {
            onResponse { response ->
                Timber.tag("HTTP status:").d("${response.status.value}")
            }
        }

        // request content-type
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

    }
}
