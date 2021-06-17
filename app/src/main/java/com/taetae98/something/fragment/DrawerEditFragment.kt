package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentDrawerEditBinding
import com.taetae98.something.dto.Drawer
import com.taetae98.something.repository.DrawerRepository
import com.taetae98.something.viewmodel.DrawerEditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DrawerEditFragment : BindingFragment<FragmentDrawerEditBinding>(R.layout.fragment_drawer_edit) {
    @Inject
    lateinit var drawerRepository: DrawerRepository

    private val viewModel by activityViewModels<DrawerEditViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnFinish()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = viewModel
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnFinish() {
        binding.setOnFinish {
            onFinish()
        }
    }

    private fun onFinish() {
        if (viewModel.name.value!!.isBlank()) {
            binding.nameTextInputLayout.error = getString(R.string.enter_the_name)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            drawerRepository.insert(Drawer.Factory.createFromDrawerEditViewModel(viewModel))
        }

        finish()
    }
}