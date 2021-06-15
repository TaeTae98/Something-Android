package com.taetae98.something.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToDoEditViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
): ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val DRAWER_ID = "drawerID"
        private const val IS_ON_TOP = "isOnTop"
        private const val IS_NOTIFICATION = "isNotification"
        private const val BEGIN_DATE = "beginDate"
        private const val END_DATE = "endDate"

        private const val HAS_TERM = "hasTerm"
        private const val HAS_DRAWER = "hasDrawer"
    }

    val title = stateHandle.getLiveData(TITLE, "")
    val description = stateHandle.getLiveData(DESCRIPTION, "")
    val drawerId = stateHandle.getLiveData<Long>(DRAWER_ID, null)
    val isOnTop = stateHandle.getLiveData(IS_ON_TOP, false)
    val isNotification = stateHandle.getLiveData(IS_NOTIFICATION, false)
    val beginDate = stateHandle.getLiveData<Date>(BEGIN_DATE, null)
    val endDate = stateHandle.getLiveData<Date>(END_DATE, null)

    val hasTerm = stateHandle.getLiveData(HAS_TERM, (beginDate.value != null && endDate.value != null))
    val hasDrawer = stateHandle.getLiveData(HAS_DRAWER, (drawerId.value != null))

    init {
        beginDate.observeForever {
            hasTerm.value = (it != null && endDate.value != null)
        }
        endDate.observeForever {
            hasTerm.value = (beginDate.value != null && it != null)
        }
        drawerId.observeForever {
            hasDrawer.value = it != null
        }
    }

    fun update(todo: ToDo) {
        title.value = todo.title
        description.value = todo.description
        drawerId.value = todo.drawerId
        isOnTop.value = todo.isOnTop
        isNotification.value = todo.isNotification
        beginDate.value = todo.beginDate
        endDate.value = todo.endDate
    }
}