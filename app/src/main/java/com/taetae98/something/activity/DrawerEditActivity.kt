package com.taetae98.something.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import com.taetae98.something.R
import com.taetae98.something.base.BindingActivity
import com.taetae98.something.databinding.ActivityDrawerEditBinding
import com.taetae98.something.viewmodel.DrawerEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawerEditActivity : BindingActivity<ActivityDrawerEditBinding>(R.layout.activity_drawer_edit) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.drawerEditFragment)
        )
    }

    private val args by navArgs<DrawerEditActivityArgs>()

    private val drawer by lazy { args.drawer }
    private val drawerEditViewModel by viewModels<DrawerEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateDrawerEditViewModel()
    }

    private fun onCreateDrawerEditViewModel() {
        drawer?.let { drawerEditViewModel.update(it) }
    }
}