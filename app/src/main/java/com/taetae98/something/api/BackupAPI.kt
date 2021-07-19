package com.taetae98.something.api

import android.content.Context
import android.content.Intent
import android.os.Build
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.taetae98.something.R
import com.taetae98.something.service.BackupService
import com.taetae98.something.service.RestoreService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupAPI @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun isAccountValid(): Boolean {
        return FirebaseAuth.getInstance().currentUser?.uid != null
    }

    suspend fun onSignInWithFirebase(data: Intent?) {
        val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
        FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(account.idToken!!, null)).await()
    }

    fun onBackup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, BackupService::class.java))
        } else {
            context.startService(Intent(context, BackupService::class.java))
        }
    }

    fun onRestore() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, RestoreService::class.java))
        } else {
            context.startService(Intent(context, RestoreService::class.java))
        }
    }

    suspend fun onSignOut() {
        FirebaseAuth.getInstance().signOut()
        getGoogleSignInClient().signOut().await()
    }

    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }
}