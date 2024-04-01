package com.pthw.data.network.ktor

/**
 * Created by P.T.H.W on 11/03/2024.
 */

// extension function to concat with baseUrl
fun String.toKtor() = "$KTOR_BASE_URL$this"

fun String.placeholders(placeholders: Map<String, String>): String {
    var result = this
    placeholders.forEach { (key, value) ->
        result = result.replace("{$key}", value)
    }
    return result
}