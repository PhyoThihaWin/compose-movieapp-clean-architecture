package com.pthw.shared.extension

fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0L
fun Double?.orZero() = this ?: 0.0
fun Boolean?.orTrue() = this ?: true
fun Boolean?.orFalse() = this ?: false
fun String.orNull() = if (this == "" || this == "null") null else this
fun String?.orInt(): Int = this?.toIntOrNull() ?: 0
fun String?.orLong(): Long = this?.toLongOrNull() ?: 0L


