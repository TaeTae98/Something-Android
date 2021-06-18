package com.taetae98.something.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentSomethingSettingBinding
import com.taetae98.something.repository.SettingRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SomethingSettingFragment : BindingFragment<FragmentSomethingSettingBinding>(R.layout.fragment_something_setting) {
    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnSticky()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.repository = settingRepository
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnSticky() {
        settingRepository.isSticky.observe(viewLifecycleOwner) {
            if (it && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(requireContext())) {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(R.string.require_permission)
                    setMessage(context.getString(R.string.request_permission))
                    setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                        startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${requireContext().packageName}")))
                        settingRepository.isSticky.value = false
                    }
                    setNegativeButton(context.getString(R.string.cancel)) { _, _ ->
                        settingRepository.isSticky.value = false
                    }
                    setCancelable(false)
                }.show()
            }
        }
    }
}