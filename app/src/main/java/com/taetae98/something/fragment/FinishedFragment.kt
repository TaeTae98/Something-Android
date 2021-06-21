package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.something.R
import com.taetae98.something.adapter.ToDoAdapter
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentFinishedBinding
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinishedFragment : BindingFragment<FragmentFinishedBinding>(R.layout.fragment_finished) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    private val todoAdapter by lazy {
        ToDoAdapter().apply {
            onToDoClickCallback = {
                findNavController().navigate(FinishedFragmentDirections.actionFinishedFragmentToTodoEditActivity(it))
            }
            onToDoLongClickCallback = {
                findNavController().navigate(FinishedFragmentDirections.actionFinishedFragmentToTodoBottomMenuDialog(it))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateToDoList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateRecyclerView()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateRecyclerView() {
        binding.recyclerView.adapter = todoAdapter
    }

    private fun onCreateToDoList() {
        todoRepository.findIsFinishedLiveData().observe(viewLifecycleOwner) {
            todoAdapter.submitList(it)
        }
    }
}