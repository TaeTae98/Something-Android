package com.taetae98.something.dto

import androidx.room.*
import com.taetae98.something.viewmodel.ToDoEditViewModel
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Drawer::class,
            parentColumns = ["drawerId"],
            childColumns = ["drawerId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["drawerId"],
            name = "todo_index_drawer_id",
            unique = false
        ),
        Index(
            value = ["isFinished"],
            name = "todo_index_is_finished"
        ),
        Index(
            value = ["isOnTop"],
            name = "todo_index_is_on_top"
        ),
        Index(
            value = ["isNotification"],
            name = "todo_index_is_notification"
        )
    ]
)
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todoId")
    var id: Long = 0L,
    @ColumnInfo(defaultValue = "")
    var title: String = "",
    @ColumnInfo(defaultValue = "")
    var description: String = "",
    @ColumnInfo(defaultValue = "null")
    var drawerId: Long? = null,
    @ColumnInfo(defaultValue = "false")
    var isOnTop: Boolean = false,
    @ColumnInfo(defaultValue = "false")
    var isNotification: Boolean = false,
    @ColumnInfo(defaultValue = "false")
    var isFinished: Boolean = false,
    @Embedded
    var term: Term? = null
) : Serializable {
    class Factory {
        companion object {
            fun createFromToDoEditViewModel(viewModel: ToDoEditViewModel): ToDo {
                return ToDo(
                    viewModel.id.value!!,
                    viewModel.title.value!!,
                    viewModel.description.value!!,
                    if (viewModel.hasDrawer.value == true) viewModel.drawerId.value else null,
                    viewModel.isOnTop.value!!,
                    viewModel.isNotification.value!!,
                    viewModel.isFinished.value!!,
                    if (viewModel.hasTerm.value == true) Term(viewModel.beginDate.value, viewModel.endDate.value) else null
                )
            }
        }
    }
}