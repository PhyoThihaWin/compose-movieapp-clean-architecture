package com.pthw.data.network.ktor

/**
 * Created by P.T.H.W on 01/04/2024.
 */

// paging
const val STARTING_PAGE_INDEX = 1
const val LOAD_DELAY_MILLIS = 6_000L

// BaseUrl
const val KTOR_BASE_URL = "https://api.themoviedb.org/"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"

// Endpoint
const val ENDPOINT_GET_NOW_PLAYING = "3/movie/now_playing"
const val ENDPOINT_GET_UP_COMING = "3/movie/upcoming"
const val ENDPOINT_POPULAR_MOVIES = "3/movie/popular"
const val ENDPOINT_POPULAR_PERSON = "3/trending/person/week"
const val ENDPOINT_MOVIE_DETAIL = "3/movie"
const val ENDPOINT_MOVIE_GENRES = "3/genre/movie/list"
const val ENDPOINT_SEARCH_MOVIES = "3/search/movie"

// Parameter
const val PARAM_PAGE = "page"
const val PARAM_QUERY = "query"
