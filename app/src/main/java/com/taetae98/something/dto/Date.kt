package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

data class Date(
    var year: Int = Calendar.getInstance().get(Calendar.YEAR),
    var month: Int = Calendar.getInstance().get(Calendar.MONTH),
    var dayOfMonth: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
) : Parcelable {
    val timeInMillis: Long
        get() {
            return GregorianCalendar(year, month, dayOfMonth).timeInMillis
        }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(year)
            writeInt(month)
            writeInt(dayOfMonth)
        }
    }

    override fun toString(): String {
        return SimpleDateFormat().format(timeInMillis)
    }

    class Factory {
        companion object {
            fun createFromTimeInMillis(timeInMillis: Long): Date {
                val calendar = GregorianCalendar().apply {
                    this.timeInMillis = timeInMillis
                }

                return Date(
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
            }
        }
    }

    companion object CREATOR : Parcelable.Creator<Date> {
        override fun createFromParcel(parcel: Parcel): Date {
            return Date(parcel)
        }

        override fun newArray(size: Int): Array<Date?> {
            return arrayOfNulls(size)
        }
    }
}