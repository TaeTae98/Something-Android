package com.taetae98.something.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taetae98.something.viewmodel.DrawerEditViewModel
import java.io.Serializable

@Entity(
    indices = [
        Index(
            value = ["password"],
            name = "drawer_index_password"
        ),
        Index(
            value = ["isVisibleInToDoFragment"],
            name = "drawer_index_is_visible_in_todo_fragment"
        ),
        Index(
            value = ["isVisibleInCalendarFragment"],
            name = "drawer_index_is_visible_in_calendar_fragment"
        )
    ]
)
data class Drawer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "drawerId")
    var id: Long = 0L,
    @ColumnInfo(defaultValue = "")
    var name: String = "",
    @ColumnInfo(defaultValue = "null")
    var password: String? = null,
    @ColumnInfo(defaultValue = "0")
    var ordinal: Long = 0L,
    @ColumnInfo(defaultValue = "true")
    var isVisibleInToDoFragment: Boolean = true,
    @ColumnInfo(defaultValue = "true")
    var isVisibleInCalendarFragment: Boolean = true,
) : Serializable {
    class Factory {
        companion object {
            fun createFromDrawerEditViewModel(viewModel: DrawerEditViewModel): Drawer {
                return Drawer(
                    viewModel.id.value!!,
                    viewModel.name.value!!,
                    if (viewModel.hasPassword.value == true) viewModel.password.value ?: "" else null,
                    viewModel.ordinal.value!!,
                    viewModel.isVisibleInToDoFragment.value!!,
                    viewModel.isVisibleInCalendarFragment.value!!
                )
            }
        }
    }
}