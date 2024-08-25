package com.pthw.shared.extension

/**
 * Created by P.T.H.W on 22/08/2024.
 */

infix fun <T> Boolean.then(valueIfTrue: T): Pair<Boolean, T> = Pair(this, valueIfTrue)
infix fun <T> Pair<Boolean, T>.or(valueIfFalse: T): T =
    if (this.first) this.second else valueIfFalse