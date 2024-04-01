package com.pthw.data.network.home

import com.pthw.data.network.home.response.MovieResponse
import com.pthw.data.network.ktor.DataResponse
import com.pthw.data.network.ktor.ENDPOINT_GET_NOW_PLAYING
import com.pthw.data.network.ktor.toKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Created by P.T.H.W on 01/04/2024.
 */

class HomeService @Inject constructor(
    private val client: HttpClient,
) {

    suspend fun getNowPlayingMovies(): String {
        val endpoint = ENDPOINT_GET_NOW_PLAYING.toKtor()
        val raw = client.get(endpoint).body<DataResponse<MovieResponse>>()
        return raw.toString()
    }
}