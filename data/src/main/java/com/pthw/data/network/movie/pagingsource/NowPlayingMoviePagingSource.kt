package com.pthw.data.network.movie.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pthw.data.network.movie.MovieApiService
import com.pthw.data.network.movie.response.MovieResponse
import kotlinx.coroutines.delay
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1
private const val LOAD_DELAY_MILLIS = 6_000L

class NowPlayingMoviePagingSource(
    private val query: String = "",
    private val apiService: MovieApiService,
) : PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val startKey = params.key ?: STARTING_PAGE_INDEX

        if (startKey != STARTING_PAGE_INDEX) {
            Timber.i("Reached delay phrase.....")
            delay(LOAD_DELAY_MILLIS)
        }

        return runCatching {
            val raw = apiService.getNowPlayingMovies(startKey)
            val results = raw.data.orEmpty()
            Timber.e("Success api-call: %s", results.size)

            // delay
            delay(300)

            LoadResult.Page(
                data = results,
                prevKey = if (startKey == STARTING_PAGE_INDEX) null else startKey - 1,
                nextKey = if (results.isEmpty()) null else startKey + 1
            )
        }.getOrElse {
            LoadResult.Error(it)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition
    }

}