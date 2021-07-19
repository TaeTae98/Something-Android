package com.taetae98.something.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.taetae98.something.R
import com.taetae98.something.TAG
import com.taetae98.something.api.BackupAPI
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentBackupBinding
import com.taetae98.something.repository.DrawerRepository
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class BackupFragment : BindingFragment<FragmentBackupBinding>(R.layout.fragment_backup) {
    @Inject
    lateinit var todoRepository: ToDoRepository
    @Inject
    lateinit var drawerRepository: DrawerRepository
    @Inject
    lateinit var backupAPI: BackupAPI

    private val onBackupResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                backupAPI.onSignInWithFirebase(it.data)
                backupAPI.onBackup()
            } catch (e: FirebaseNetworkException) {
                Log.e(TAG, "onBackupResult Error", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), R.string.connect_to_the_internet, Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Log.e(TAG, "onBackupResult Error", e)
                withContext(Dispatchers.Main) {
                    when (e.statusCode) {
                        7 -> {
                            Toast.makeText(requireContext(), R.string.connect_to_the_internet, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(), R.string.google_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onBackupResult Error", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val onRestoreResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                backupAPI.onSignInWithFirebase(it.data)
                backupAPI.onRestore()
            } catch (e: FirebaseNetworkException) {
                Log.e(TAG, "onBackupResult Error", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), R.string.connect_to_the_internet, Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Log.e(TAG, "onBackupResult Error", e)
                withContext(Dispatchers.Main) {
                    when (e.statusCode) {
                        7 -> {
                            Toast.makeText(requireContext(), R.string.connect_to_the_internet, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(), R.string.google_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onBackupResult Error", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.backup)
                .setMessage(R.string.data_on_the_server_will_be_deleted)
                .setNegativeButton(R.string.cancel) { _, _->

                }
                .setPositiveButton(R.string.ok) { _, _ ->
                    if (backupAPI.isAccountValid()) {
                        backupAPI.onBackup()
                    } else {
                        onBackupResult.launch(backupAPI.getGoogleSignInClient().signInIntent)
                    }
                }
                .show()
        }
    }

    private fun onCreateOnRestore() {
        binding.setOnRestore {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.restore)
                .setMessage(R.string.data_on_the_device_will_be_deleted)
                .setNegativeButton(R.string.cancel) { _, _->

                }
                .setPositiveButton(R.string.ok) { _, _ ->
                    if (backupAPI.isAccountValid()) {
                        backupAPI.onRestore()
                    } else {
                        onRestoreResult.launch(backupAPI.getGoogleSignInClient().signInIntent)
                    }
                }
                .show()
        }
    }

    private fun onCreateOnSignOut() {
        binding.setOnSignOut {
            CoroutineScope(Dispatchers.IO).launch {
                backupAPI.onSignOut()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), R.string.sign_out_success, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}