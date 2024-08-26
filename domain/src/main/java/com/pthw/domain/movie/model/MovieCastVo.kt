package com.pthw.domain.movie.model

/**
 * Created by P.T.H.W on 24/04/2024.
 */
data class MovieCastVo(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
    val castId: Int,
    val character: String,
    val creditId: String,
    val order: Int
) {
    companion object {
        val fakeMovieCastList = listOf(
            MovieCastVo(
                adult = false,
                gender = 1,
                id = 101,
                knownForDepartment = "Acting",
                name = "Jane Doe",
                originalName = "Jane Doe",
                popularity = 8.5,
                profilePath = "/path/to/profile1.jpg",
                castId = 1,
                character = "Protagonist",
                creditId = "credit123",
                order = 0
            ),
            MovieCastVo(
                adult = false,
                gender = 2,
                id = 102,
                knownForDepartment = "Acting",
                name = "John Smith",
                originalName = "John Smith",
                popularity = 7.9,
                profilePath = "/path/to/profile2.jpg",
                castId = 2,
                character = "Antagonist",
                creditId = "credit124",
                order = 1
            ),
            MovieCastVo(
                adult = false,
                gender = 1,
                id = 103,
                knownForDepartment = "Acting",
                name = "Alice Johnson",
                originalName = "Alice Johnson",
                popularity = 9.2,
                profilePath = "/path/to/profile3.jpg",
                castId = 3,
                character = "Sidekick",
                creditId = "credit125",
                order = 2
            ),
            MovieCastVo(
                adult = false,
                gender = 2,
                id = 104,
                knownForDepartment = "Directing",
                name = "Michael Brown",
                originalName = "Michael Brown",
                popularity = 6.8,
                profilePath = "/path/to/profile4.jpg",
                castId = 4,
                character = "Director",
                creditId = "credit126",
                order = 3
            ),
            MovieCastVo(
                adult = false,
                gender = 1,
                id = 105,
                knownForDepartment = "Acting",
                name = "Sophia Davis",
                originalName = "Sophia Davis",
                popularity = 7.5,
                profilePath = "/path/to/profile5.jpg",
                castId = 5,
                character = "Mentor",
                creditId = "credit127",
                order = 4
            )
        )

    }
}