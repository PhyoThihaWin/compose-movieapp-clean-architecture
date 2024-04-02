package com.pthw.data.network.ktor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0YjgzYjQ1NTVlZWE3OTgwYTVhMmM5ODE0Y2RiMWJjMSIsInN1YiI6IjY0MDAxZTU0Njk5ZmI3MDBjNjRhYTBhNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ds757UkPK8y4EAO1RL_0vG_70X77nt8lenLL0t_Yy1E"
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer $token")
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}
