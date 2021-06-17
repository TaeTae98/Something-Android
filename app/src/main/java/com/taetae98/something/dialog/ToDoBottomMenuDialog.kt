package com.taetae98.something.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.something.R
import com.taetae98.something.activity.ToDoEditActivity
import com.taetae98.something.base.BindingBottomSheetDialog
import com.taetae98.something.databinding.DialogTodoBottomMenuBinding
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ToDoBottomMenuDialog : BindingBottomSheetDialog<DialogTodoBottomMenuBinding>(R.layout.dialog_todo_bottom_menu) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    private val args by navArgs<ToDoBottomMenuDialogArgs>()
    private val todo by lazy { args.todo }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnEdit()
        onCreateOnDelete()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.todo = todo
    }

    private fun onCreateOnEdit() {
        binding.setOnEdit {
            startActivity(
                    Intent(requireContext(), ToDoEditActivity::class.java).apply {
                        putExtra(ToDoEditActivity.TODO, todo)
                    }
            )
            
            findNavController().navigateUp()
        }
    }

    private fun onCreateOnDelete() {
        binding.setOnDelete {
            CoroutineScope(Dispatchers.IO).launch {
                todoRepository.delete(todo)
            }

            findNavController().navigateUp()
        }
    }
}