package com.taetae98.something.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val PASSWORD = "PASSWORD"
    }

    val password = stateHandle.getLiveData(PASSWORD, "")
}