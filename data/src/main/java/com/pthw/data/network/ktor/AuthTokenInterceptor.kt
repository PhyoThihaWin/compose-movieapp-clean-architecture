package com.pthw.data.network.ktor

//import com.pthw.domain.repository.AuthStoreProvider
//import okhttp3.Interceptor
//import okhttp3.Response
//import javax.inject.Inject
//
//class AuthTokenInterceptor @Inject constructor(
//    private val authStore: AuthStoreProvider
//) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val token = authStore.getAuthToken() ?: return chain.proceed(chain.request())
//        val requestBuilder = chain.request().newBuilder()
//        requestBuilder.addHeader("Authorization", "Bearer $token")
//        val newRequest = requestBuilder.build()
//        return chain.proceed(newRequest)
//    }
//}
