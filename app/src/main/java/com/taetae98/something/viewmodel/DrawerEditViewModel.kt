package com.taetae98.something.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.something.dto.Drawer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrawerEditViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): ViewModel() {
    companion object {
        private const val ID = "ID"
        private const val NAME = "NAME"
        private const val PASSWORD = "PASSWORD"
        private const val IS_VISIBLE_IN_TODO_FRAGMENT = "IS_VISIBLE_IN_TODO_FRAGMENT"
        private const val IS_VISIBLE_IN_CALENDAR_FRAGMENT = "IS_VISIBLE_IN_CALENDAR_FRAGMENT"

        private const val HAS_PASSWORD = "HAS_PASSWORD"
    }

    val id = stateHandle.getLiveData(ID, 0L)
    val name = stateHandle.getLiveData(NAME, "")
    val password = stateHandle.getLiveData<String>(PASSWORD, null)
    val isVisibleInToDoFragment = stateHandle.getLiveData(IS_VISIBLE_IN_TODO_FRAGMENT, true)
    val isVisibleInCalendarFragment = stateHandle.getLiveData(IS_VISIBLE_IN_CALENDAR_FRAGMENT, true)

    val hasPassword = stateHandle.getLiveData(HAS_PASSWORD, false)

    fun update(drawer: Drawer) {
        id.value = drawer.id
        name.value = drawer.name
        password.value = drawer.password
        isVisibleInToDoFragment.value = drawer.isVisibleInToDoFragment
        isVisibleInCalendarFragment.value = drawer.isVisibleInCalendarFragment

        hasPassword.value = drawer.password != null
    }
}