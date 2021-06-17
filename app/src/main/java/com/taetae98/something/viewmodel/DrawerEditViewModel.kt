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
        private const val ID = "id"
        private const val NAME = "name"
        private const val HAS_PASSWORD = "hasPassword"
        private const val PASSWORD = "password"
    }

    val id = stateHandle.getLiveData(ID, 0L)
    val name = stateHandle.getLiveData(NAME, "")
    val hasPassword = stateHandle.getLiveData(HAS_PASSWORD, false)
    val password = stateHandle.getLiveData<String>(PASSWORD, null)

    fun update(drawer: Drawer) {
        id.value = drawer.id
        name.value = drawer.name
        hasPassword.value = drawer.hasPassword
        password.value = drawer.password
    }
}