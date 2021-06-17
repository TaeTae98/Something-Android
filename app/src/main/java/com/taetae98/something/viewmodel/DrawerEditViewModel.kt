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
        private const val HAS_PASSWORD = "HAS_PASSWORD"
        private const val PASSWORD = "PASSWORD"
    }

    val id = stateHandle.getLiveData(ID, 0L)
    val name = stateHandle.getLiveData(NAME, "")
    val hasPassword = stateHandle.getLiveData(HAS_PASSWORD, false)
    val password = stateHandle.getLiveData<String>(PASSWORD, null)

    fun update(drawer: Drawer) {
        id.value = drawer.id
        name.value = drawer.name
        hasPassword.value = drawer.password != null
        password.value = drawer.password
    }
}