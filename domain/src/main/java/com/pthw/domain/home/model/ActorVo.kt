package com.pthw.domain.home.model

/**
 * Created by P.T.H.W on 02/04/2024.
 */
data class ActorVo(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
) {
    companion object {
        val fakeActors = listOf(
            ActorVo(
                adult = false,
                gender = 1,
                id = 101,
                knownForDepartment = "Acting",
                name = "Emma Stone",
                originalName = "Emily Jean Stone",
                popularity = 85.6,
                profilePath = "/images/emma_stone_profile.jpg"
            ),
            ActorVo(
                adult = false,
                gender = 2,
                id = 102,
                knownForDepartment = "Acting",
                name = "Robert Downey Jr.",
                originalName = "Robert John Downey Jr.",
                popularity = 95.3,
                profilePath = "/images/robert_downey_profile.jpg"
            ),
            ActorVo(
                adult = false,
                gender = 1,
                id = 103,
                knownForDepartment = "Acting",
                name = "Scarlett Johansson",
                originalName = "Scarlett Ingrid Johansson",
                popularity = 92.8,
                profilePath = "/images/scarlett_johansson_profile.jpg"
            ),
            ActorVo(
                adult = false,
                gender = 2,
                id = 104,
                knownForDepartment = "Acting",
                name = "Tom Hanks",
                originalName = "Thomas Jeffrey Hanks",
                popularity = 89.4,
                profilePath = "/images/tom_hanks_profile.jpg"
            ),
            ActorVo(
                adult = false,
                gender = 1,
                id = 105,
                knownForDepartment = "Acting",
                name = "Natalie Portman",
                originalName = "Neta-Lee Hershlag",
                popularity = 78.2,
                profilePath = "/images/natalie_portman_profile.jpg"
            )
        )

    }
}
