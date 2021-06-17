package com.taetae98.something.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import com.taetae98.something.R
import com.taetae98.something.base.BindingActivity
import com.taetae98.something.databinding.ActivityToDoEditBinding
import com.taetae98.something.viewmodel.ToDoEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoEditActivity : BindingActivity<ActivityToDoEditBinding>(R.layout.activity_to_do_edit) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.toDoEditFragment)
        )
    }

    private val args by navArgs<ToDoEditActivityArgs>()

    private val todo by lazy { args.todo }
    private val todoEditViewModel by viewModels<ToDoEditViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateToDoEditViewModel()
    }

    private fun onCreateToDoEditViewModel() {
        todo?.let { todoEditViewModel.update(it) }
    }
}