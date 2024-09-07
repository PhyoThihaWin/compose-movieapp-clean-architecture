package com.pthw.data.local.realmdb

import com.pthw.data.local.realmdb.entity.ActorRealmEntity
import com.pthw.data.local.realmdb.entity.GenreRealmEntity
import com.pthw.data.local.realmdb.entity.MovieRealmEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

/**
 * Created by P.T.H.W on 03/09/2024.
 */
val RealmDatabase = Realm.open(
    RealmConfiguration
        .Builder(
            schema = setOf(
                MovieRealmEntity::class,
                ActorRealmEntity::class,
                GenreRealmEntity::class
            )
        )
        .name("movies.realm")
        .build()
)
