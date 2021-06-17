package com.taetae98.something.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.taetae98.something.viewmodel.ToDoEditViewModel

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Drawer::class,
            parentColumns = ["id"],
            childColumns = ["drawerId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["drawerId"],
            name = "index_drawer_id",
            unique = false
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
    @Embedded
    var term: Term? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Term::class.java.classLoader),
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
            writeParcelable(term, flags)
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

    class Factory {
        companion object {
            fun createFromToDoEditViewModel(viewModel: ToDoEditViewModel): ToDo {
                return ToDo(
                    id = viewModel.id.value ?: 0L,
                    title = viewModel.title.value ?: "",
                    description = viewModel.description.value ?: "",
                    drawerId = viewModel.drawerId.value,
                    isOnTop = viewModel.isOnTop.value ?: false,
                    isNotification = viewModel.isNotification.value ?: false,
                    isFinished = false,
                    term = if (viewModel.hasTerm.value == true) Term(viewModel.beginDate.value, viewModel.endDate.value) else null
                )
            }
        }
    }
}