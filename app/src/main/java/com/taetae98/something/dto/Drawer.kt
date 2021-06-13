package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Drawer(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String = "",
    var isSecure: Boolean = false,
    var password: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeLong(id)
            writeString(name)
            writeByte(if (isSecure) 1 else 0)
            writeString(password)
        }
    }

    companion object CREATOR : Parcelable.Creator<Drawer> {
        override fun createFromParcel(parcel: Parcel): Drawer {
            return Drawer(parcel)
        }

        override fun newArray(size: Int): Array<Drawer?> {
            return arrayOfNulls(size)
        }
    }
}