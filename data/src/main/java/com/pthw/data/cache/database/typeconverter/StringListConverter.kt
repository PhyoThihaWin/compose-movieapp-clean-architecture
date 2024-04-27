package com.pthw.data.cache.database.typeconverter

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return data.split(",").map { it }
    }
}