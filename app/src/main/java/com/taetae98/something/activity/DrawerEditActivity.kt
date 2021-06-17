package com.taetae98.something.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.taetae98.something.R
import com.taetae98.something.base.BindingActivity
import com.taetae98.something.databinding.ActivityDrawerEditBinding
import com.taetae98.something.dto.Drawer
import com.taetae98.something.viewmodel.DrawerEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawerEditActivity : BindingActivity<ActivityDrawerEditBinding>(R.layout.activity_drawer_edit) {
    private val drawerEditViewModel by viewModels<DrawerEditViewModel>()

    companion object {
        const val DRAWER = "drawer"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateDrawerEditViewModel()
    }

    private fun onCreateDrawerEditViewModel() {
        intent.getParcelableExtra<Drawer>(DRAWER)?.let {
            drawerEditViewModel.update(it)
        }
    }
}