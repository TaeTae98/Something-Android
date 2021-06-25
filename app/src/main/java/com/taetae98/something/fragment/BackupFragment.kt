package com.taetae98.something.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.taetae98.something.R
import com.taetae98.something.TAG
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentBackupBinding

class BackupFragment : BindingFragment<FragmentBackupBinding>(R.layout.fragment_backup) {
    private val onBackupResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        try {
            onSignInWithFirebase(it.data)
            onBackup()
        } catch (e: Exception) {
            Log.e(TAG, "Google SignIn Fail", e)
        }
    }
    private val onRestoreResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        try {
            onSignInWithFirebase(it.data)
            onRestore()
        } catch (e: Exception) {
            Log.e(TAG, "Google SignIn Fail", e)
        }
    }

    companion object {
        private const val BACKUP = 1 shl 0
        private const val RESTORE = 1 shl 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnBackup()
        onCreateOnRestore()
        onCreateOnSignOut()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnBackup() {
        binding.setOnBackup {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                onSignInWithGoogle(BACKUP)
            } else {
                onBackup()
            }
        }
    }

    private fun onCreateOnRestore() {
        binding.setOnRestore {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                onSignInWithGoogle(RESTORE)
            } else {
                onRestore()
            }
        }
    }

    private fun onCreateOnSignOut() {
        binding.setOnSignOut {
            onSignOut()
        }
    }

    private fun onSignInWithGoogle(action: Int) {
        val signInIntent = getGoogleSignInClient().signInIntent

        when (action) {
            BACKUP -> {
                onBackupResult.launch(signInIntent)
            }
            RESTORE -> {
                onRestoreResult.launch(signInIntent)
            }
        }
    }

    private fun onSignInWithFirebase(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)!!

        FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(account.idToken!!, null))
    }

    private fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun onBackup() {
        Toast.makeText(requireContext(), R.string.backup, Toast.LENGTH_SHORT).show()
    }

    private fun onRestore() {
        Toast.makeText(requireContext(), R.string.restore, Toast.LENGTH_SHORT).show()
    }

    private fun onSignOut() {
        FirebaseAuth.getInstance().signOut()
        getGoogleSignInClient().signOut()

        Snackbar.make(binding.toolbar, R.string.sign_out, Snackbar.LENGTH_SHORT).show()
    }
}