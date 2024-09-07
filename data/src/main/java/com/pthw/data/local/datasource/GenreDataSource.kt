package com.pthw.data.local.datasource

import com.pthw.data.network.movie.response.GenresResponse
import com.pthw.domain.movie.model.GenreVo

/**
 * Created by P.T.H.W on 06/09/2024.
 */
interface GenreDataSource {
    suspend fun insertGenres(list: List<GenresResponse.Genre?>)
    suspend fun getGenreById(id: Int): GenreVo
    suspend fun getAllGenres(): List<GenreVo>
}