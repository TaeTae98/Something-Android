package com.taetae98.something.dto

import androidx.room.ColumnInfo
import java.io.Serializable

data class Term(
    @ColumnInfo(defaultValue = "null")
    var beginDate: Date? = null,
    @ColumnInfo(defaultValue = "null")
    var endDate: Date? = null
) : Serializable {
    override fun toString(): String {
        return when {
            beginDate == null || endDate == null -> {
                ""
            }
            beginDate == endDate -> {
                beginDate.toString()
            }
            else -> {
                "$beginDate ~ $endDate"
            }
        }
    }
}