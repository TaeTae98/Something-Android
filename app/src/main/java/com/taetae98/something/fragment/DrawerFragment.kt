package com.taetae98.something.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.something.R
import com.taetae98.something.activity.DrawerEditActivity
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentDrawerBinding

class DrawerFragment : BindingFragment<FragmentDrawerBinding>(R.layout.fragment_drawer) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateRecyclerView()
        onCreateOnDrawerAdd()
        onCreateDrawerList()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateRecyclerView() {

    }

    private fun onCreateOnDrawerAdd() {
        binding.setOnDrawerAdd {
            startActivity(Intent(requireContext(), DrawerEditActivity::class.java))
        }
    }

    private fun onCreateDrawerList() {

    }
}