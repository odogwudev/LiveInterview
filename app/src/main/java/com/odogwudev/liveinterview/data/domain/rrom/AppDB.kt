package com.odogwudev.liveinterview.data.domain.rrom

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CharacterRicks::class], version = 2, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

class Converters{
    @TypeConverters
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverters
    fun toList(value: String) : List<String> {
        return value.split(",")
    }
}