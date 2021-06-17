package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taetae98.something.viewmodel.DrawerEditViewModel

@Entity
data class Drawer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "drawerId")
    var id: Long = 0L,
    var name: String = "",
    var password: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeLong(id)
            writeString(name)
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
                    if (viewModel.hasPassword.value == true) viewModel.password.value else null
                )
            }
        }
    }
}