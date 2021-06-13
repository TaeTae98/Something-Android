package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Drawer::class,
            parentColumns = ["id"],
            childColumns = ["drawerId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var drawerId: Long? = null,
    var isOnTop: Boolean = false,
    var isNotification: Boolean = false,
    var isFinished: Boolean = false,
    var beginDate: Date? = null,
    var endDate: Date? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Date::class.java.classLoader),
        parcel.readParcelable(Date::class.java.classLoader)
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeLong(id)
            writeString(title)
            writeString(description)
            writeLong(drawerId ?: 0)
            writeByte(if (isFinished) 1 else 0)
            writeByte(if (isOnTop) 1 else 0)
            writeByte(if (isNotification) 1 else 0)
            writeParcelable(beginDate, flags)
            writeParcelable(endDate, flags)
        }
    }

    companion object CREATOR : Parcelable.Creator<ToDo> {
        override fun createFromParcel(parcel: Parcel): ToDo {
            return ToDo(parcel)
        }

        override fun newArray(size: Int): Array<ToDo?> {
            return arrayOfNulls(size)
        }
    }
}