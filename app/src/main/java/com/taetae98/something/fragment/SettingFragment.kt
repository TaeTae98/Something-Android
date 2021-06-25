package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentSettingBinding

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnSomething()
        onCreateOnBackup()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnSomething() {
        binding.setOnSomething {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToSomethingSettingFragment())
        }
    }

    private fun onCreateOnBackup() {
        binding.setOnBackup {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToBackupFragment())
        }
    }
}