package com.taetae98.something.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.taetae98.something.R
import com.taetae98.something.base.BindingActivity
import com.taetae98.something.databinding.ActivityToDoEditBinding
import com.taetae98.something.dto.ToDo
import com.taetae98.something.viewmodel.ToDoEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoEditActivity : BindingActivity<ActivityToDoEditBinding>(R.layout.activity_to_do_edit) {
    private val todoEditViewModel by viewModels<ToDoEditViewModel>()

    companion object {
        const val TODO = "todo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateToDoEditViewModel()
    }

    private fun onCreateToDoEditViewModel() {
        intent.getParcelableExtra<ToDo>(TODO)?.let {
            todoEditViewModel.update(it)
        }
    }
}