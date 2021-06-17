package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.something.R
import com.taetae98.something.adapter.DrawerAdapter
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentDrawerBinding
import com.taetae98.something.dialog.VerifyPasswordDialog
import com.taetae98.something.repository.DrawerRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrawerFragment : BindingFragment<FragmentDrawerBinding>(R.layout.fragment_drawer) {
    @Inject
    lateinit var drawerRepository: DrawerRepository

    private val drawerAdapter by lazy {
        DrawerAdapter().apply {
            onDrawerClickCallback = {
                if (it.password != null) {
                    VerifyPasswordDialog(it.password!!) {
                        findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToDrawerToDoFragment(it))
                    }.show(parentFragmentManager, null)
                } else {
                    findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToDrawerToDoFragment(it))
                }
            }

            onDrawerLongClickCallback = {
                if (it.password != null) {
                    VerifyPasswordDialog(it.password!!) {
                        findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToDrawerBottomMenuDialog(it))
                    }.show(parentFragmentManager, null)
                } else {
                    findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToDrawerBottomMenuDialog(it))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateDrawerList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateRecyclerView()
        onCreateOnDrawerAdd()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateRecyclerView() {
        with(binding.recyclerView) {
            adapter = drawerAdapter
        }
    }

    private fun onCreateOnDrawerAdd() {
        binding.setOnDrawerAdd {
            findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToDrawerEditActivity())
        }
    }

    private fun onCreateDrawerList() {
        drawerRepository.findAllLiveData().observe(viewLifecycleOwner) {
            drawerAdapter.submitList(it)
        }
    }
}