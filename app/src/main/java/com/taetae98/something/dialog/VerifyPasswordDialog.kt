package com.taetae98.something.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.taetae98.something.R
import com.taetae98.something.base.BindingDialog
import com.taetae98.something.databinding.DialogVerifyPasswordBinding
import com.taetae98.something.viewmodel.PasswordViewModel

class VerifyPasswordDialog(
    private val password: String = "",
    private val onSuccessCallback: (() -> Unit)? = null
) : BindingDialog<DialogVerifyPasswordBinding>(R.layout.dialog_verify_password) {
    private val viewModel by viewModels<PasswordViewModel>()

    override fun onResume() {
        super.onResume()
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnCancel()
        onCreateOnVerify()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = viewModel
    }

    private fun onCreateOnCancel() {
        binding.setOnCancel {
            dismissAllowingStateLoss()
        }
    }

    private fun onCreateOnVerify() {
        binding.setOnVerify {
            if (password == viewModel.password.value) {
                onSuccessCallback?.invoke()
                dismissAllowingStateLoss()
            } else {
                binding.passwordTextInputLayout.error = getString(R.string.invalid_password)
            }
        }
    }
}