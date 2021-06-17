package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo

data class Term(
    @ColumnInfo(defaultValue = "null")
    var beginDate: Date? = null,
    @ColumnInfo(defaultValue = "null")
    var endDate: Date? = null
) : Parcelable {
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

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Date::class.java.classLoader),
        parcel.readParcelable(Date::class.java.classLoader)
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(beginDate, flags)
        dest.writeParcelable(endDate, flags)
    }

    companion object CREATOR : Parcelable.Creator<Term> {
        override fun createFromParcel(parcel: Parcel): Term {
            return Term(parcel)
        }

        override fun newArray(size: Int): Array<Term?> {
            return arrayOfNulls(size)
        }
    }
}