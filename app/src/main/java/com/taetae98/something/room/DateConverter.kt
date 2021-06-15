package com.taetae98.something.room

import androidx.room.TypeConverter
import com.taetae98.something.dto.Date

class DateConverter {
    @TypeConverter
    fun dateToTimeInMillis(date: Date?): Long? {
        return date?.timeInMillis
    }

    @TypeConverter
    fun timeInMillisToDate(timeInMillis: Long?): Date? {
        return timeInMillis?.let {
            Date.Factory.createFromTimeInMillis(it)
        }
    }
}