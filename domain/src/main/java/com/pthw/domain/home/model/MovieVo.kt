package com.pthw.domain.home.model

import kotlinx.serialization.Serializable

/**
 * Created by P.T.H.W on 02/04/2024.
 */
@Serializable
data class MovieVo(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val genreIds: List<String>,
    val isFavorite: Boolean = false
) : java.io.Serializable {
    companion object {
        val fakeMovies = listOf(
            MovieVo(
                id = 1,
                title = "The Adventures of ChatGPT",
                overview = "An AI-powered chatbot embarks on a journey to understand human emotions and helps people around the world.",
                backdropPath = "/images/adventures_backdrop.jpg",
                posterPath = "/images/adventures_poster.jpg",
                releaseDate = "2023-08-01",
                voteAverage = 8.5f,
                genreIds = listOf("Sci-Fi", "Adventure"),
                isFavorite = true
            ),
            MovieVo(
                id = 2,
                title = "Mystery of the Lost Code",
                overview = "A group of hackers race against time to uncover the secrets hidden in a lost piece of code.",
                backdropPath = "/images/lost_code_backdrop.jpg",
                posterPath = "/images/lost_code_poster.jpg",
                releaseDate = "2022-11-15",
                voteAverage = 7.9f,
                genreIds = listOf("Thriller", "Mystery"),
                isFavorite = false
            ),
            MovieVo(
                id = 3,
                title = "Love in the Digital Age",
                overview = "In a world dominated by technology, two souls find love through a series of unexpected online encounters.",
                backdropPath = "/images/digital_age_backdrop.jpg",
                posterPath = "/images/digital_age_poster.jpg",
                releaseDate = "2024-02-14",
                voteAverage = 7.2f,
                genreIds = listOf("Romance", "Drama"),
                isFavorite = true
            ),
            MovieVo(
                id = 4,
                title = "The Last Stand",
                overview = "A retired soldier is called back to action to save the world from an impending threat.",
                backdropPath = "/images/last_stand_backdrop.jpg",
                posterPath = "/images/last_stand_poster.jpg",
                releaseDate = "2021-07-04",
                voteAverage = 8.0f,
                genreIds = listOf("Action", "Adventure"),
                isFavorite = false
            ),
            MovieVo(
                id = 5,
                title = "Echoes of the Past",
                overview = "A historian discovers a time-traveling artifact that reveals the truth about ancient civilizations.",
                backdropPath = "/images/echoes_backdrop.jpg",
                posterPath = "/images/echoes_poster.jpg",
                releaseDate = "2020-10-31",
                voteAverage = 7.5f,
                genreIds = listOf("Sci-Fi", "Historical"),
                isFavorite = false
            )
        )

    }
}
