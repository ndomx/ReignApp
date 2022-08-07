package com.ndomx.reign.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun dateFromTimestamp(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}