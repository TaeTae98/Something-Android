package com.taetae98.something.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.something.R
import com.taetae98.something.base.BindingBottomSheetDialog
import com.taetae98.something.databinding.DialogDrawerBottomMenuBinding
import com.taetae98.something.repository.DrawerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DrawerBottomMenuDialog : BindingBottomSheetDialog<DialogDrawerBottomMenuBinding>(R.layout.dialog_drawer_bottom_menu) {
    @Inject
    lateinit var drawerRepository: DrawerRepository

    private val args by navArgs<DrawerBottomMenuDialogArgs>()
    private val drawer by lazy { args.drawer }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnEdit()
        onCreateOnDelete()

        return binding.root
    }

    private fun onCreateOnEdit() {
        binding.setOnEdit {
            findNavController().navigate(DrawerBottomMenuDialogDirections.actionDrawerBottomMenuDialogToDrawerEditActivity(drawer))
        }
    }

    private fun onCreateOnDelete() {
        binding.setOnDelete {
            CoroutineScope(Dispatchers.IO).launch {
                drawerRepository.delete(drawer)
            }

            findNavController().navigateUp()
        }
    }
}