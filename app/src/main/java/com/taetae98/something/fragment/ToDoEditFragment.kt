package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentTodoEditBinding

class ToDoEditFragment : BindingFragment<FragmentTodoEditBinding>(R.layout.fragment_todo_edit) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }
}