package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taetae98.something.viewmodel.DrawerEditViewModel

@Entity
class Drawer(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String = "",
    var hasPassword: Boolean = false,
    var password: String? = null,
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
            writeByte(if (hasPassword) 1 else 0)
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

    class Factory {
        companion object {
            fun createFromDrawerEditViewModel(viewModel: DrawerEditViewModel): Drawer {
                return Drawer(
                    viewModel.id.value ?: 0L,
                    viewModel.name.value ?: "",
                    viewModel.hasPassword.value ?: false,
                    viewModel.password.value ?: ""
                )
            }
        }
    }
}