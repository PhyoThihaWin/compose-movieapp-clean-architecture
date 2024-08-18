package com.pthw.data.network.movie

import com.pthw.data.network.movie.response.ActorResponse
import com.pthw.data.network.movie.response.MovieResponse
import com.pthw.data.network.movie.response.GenresResponse
import com.pthw.data.network.movie.response.MovieDetailCreditsResponse
import com.pthw.data.network.movie.response.MovieDetailResponse
import com.pthw.data.network.ktor.DataResponse
import com.pthw.data.network.ktor.ENDPOINT_GET_NOW_PLAYING
import com.pthw.data.network.ktor.ENDPOINT_GET_UP_COMING
import com.pthw.data.network.ktor.ENDPOINT_MOVIE_DETAIL
import com.pthw.data.network.ktor.ENDPOINT_MOVIE_GENRES
import com.pthw.data.network.ktor.ENDPOINT_POPULAR_MOVIES
import com.pthw.data.network.ktor.ENDPOINT_POPULAR_PERSON
import com.pthw.data.network.ktor.PARAM_PAGE
import com.pthw.data.network.ktor.toKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class MovieApiService(private val client: HttpClient) {
    suspend fun getNowPlayingMovies(page: Int = 1): DataResponse<List<MovieResponse>> {
        val endpoint = ENDPOINT_GET_NOW_PLAYING.toKtor()
        return client.get(endpoint) {
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getUpComingMovies(page: Int = 1): DataResponse<List<MovieResponse>> {
        val endpoint = ENDPOINT_GET_UP_COMING.toKtor()
        return client.get(endpoint){
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getPopularMovies(page: Int = 1): DataResponse<List<MovieResponse>> {
        val endpoint = ENDPOINT_POPULAR_MOVIES.toKtor()
        return client.get(endpoint){
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getPopularPeople(page: Int = 1): DataResponse<List<ActorResponse>> {
        val endpoint = ENDPOINT_POPULAR_PERSON.toKtor()
        return client.get(endpoint){
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getMovieDetails(movieId: String): MovieDetailResponse {
        val endpoint = ENDPOINT_MOVIE_DETAIL.toKtor()
        return client.get(endpoint) {
            url {
                appendPathSegments(movieId)
            }
        }.body()
    }

    suspend fun getMovieDetailCasts(movieId: String): MovieDetailCreditsResponse {
        val endpoint = ENDPOINT_MOVIE_DETAIL.toKtor()
        return client.get(endpoint) {
            url {
                appendPathSegments(movieId, "credits")
            }
        }.body()
    }

    suspend fun getMovieGenres(): GenresResponse {
        val endpoint = ENDPOINT_MOVIE_GENRES.toKtor()
        return client.get(endpoint).body()
    }
}