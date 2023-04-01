package com.hayohtee.notes.data

import androidx.room.TypeConverter
import java.util.*

class NoteTypeConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}