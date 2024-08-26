package com.pthw.domain.movie.model

/**
 * Created by P.T.H.W on 24/04/2024.
 */
data class MovieDetailVo(
    val id: Int,
    val adult: Boolean,
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val runtime: Int,
    val releaseDate: String,
    val voteAverage: Double,
    val genres: List<Genre>,
    val originalLanguage: String,
    val overview: String,
    val casts: List<MovieCastVo>,
    val crews: List<MovieCastVo>
) {
    data class Genre(
        val id: Int,
        val name: String
    )

    companion object {
        fun fake() = MovieDetailVo(
            adult = true,
            backdropPath = "/pEL9MKMmGRoUu788JcqPGEfzUOE.jpg",
            genres = emptyList(),
            id = 63842,
            originalLanguage = "en",
            overview = "With its unique camera work, \"Jack's POV 14\" brings you into the action and doesn't loosen its grip until you're completely drained. \"Jack's POV 14\", starring Digital Playground's beautiful contract girl Riley Steele gives you the best action, the most delicious teases, and pure in-your-face comedy. It's your job to cast sexy, young women like Riley Steele in music videos, resulting in you nailing gorgeous ass after even more succulent pussy. Let Jack's camera tease and tempt you, offering the finest bush on the planet, all in first hand perspective. \"Jack's POV 14\" stars 5 ready and willing hot girls: Riley Steele, Alexis Texas, Codi Carmichael, Kristina Rose, and Nicole Ray. Become Jack for a day and look into Riley's bright, blue eyes as she sucks your giant cock dry!",
            posterPath = "/aNBmUJqwNT1zgIoFTvZYwMlwNEx.jpg",
            releaseDate = "2009-07-28",
            runtime = 139,
            title = "Jack's POV 14",
            voteAverage = 7.8,
            casts = MovieCastVo.fakeMovieCastList,
            crews = MovieCastVo.fakeMovieCastList
        )
    }
}
