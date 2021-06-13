package com.taetae98.something.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import com.taetae98.something.R
import com.taetae98.something.activity.ToDoEditActivity
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentTodoBinding

class ToDoFragment : BindingFragment<FragmentTodoBinding>(R.layout.fragment_todo) {
    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_todo_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.todoEditFragment -> {
                startActivity(Intent(requireContext(), ToDoEditActivity::class.java))
                true
            }
            else -> {
                false
            }
        }
    }
}