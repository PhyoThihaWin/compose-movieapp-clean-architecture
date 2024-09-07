package com.pthw.data.local.realmdb.entity

import com.pthw.domain.home.model.MovieVo
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * Created by P.T.H.W on 05/09/2024.
 */
class MovieRealmEntity : RealmObject {
    @PrimaryKey
    var tableId: ObjectId = ObjectId()
    var id: Int = 0
    var title: String = ""
    var overview: String = ""
    var backdropPath: String = ""
    var posterPath: String = ""
    var releaseDate: String = ""
    var voteAverage: Double = 0.0
    var genreIds: RealmList<String> = realmListOf()

    var isFavorite: Boolean = false
    var isNowPlaying: Boolean = false
    var isUpComing: Boolean = false
    var isPopular: Boolean = false

    fun toMovieVo() = MovieVo(
        id = id,
        title = title,
        overview = overview,
        backdropPath = backdropPath,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage.toFloat(),
        genreIds = genreIds.toRealmList(),
        isFavorite = isFavorite
    )
}