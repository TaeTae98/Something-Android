package com.taetae98.something.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.taetae98.something.manager.NotificationManager
import com.taetae98.something.repository.DrawerRepository
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class BackupService : Service() {
    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var todoRepository: ToDoRepository

    @Inject
    lateinit var drawerRepository: DrawerRepository

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NotificationManager.BACKUP_IN_PROGRESS_NOTIFICATION_ID, notificationManager.createBackupNotification())
        CoroutineScope(Dispatchers.IO).launch {
            val id = FirebaseAuth.getInstance().currentUser!!.uid

            FirebaseDatabase.getInstance().getReference(id).child("todo").setValue(todoRepository.findAll()).await()
            FirebaseDatabase.getInstance().getReference(id).child("drawer").setValue(drawerRepository.findAll()).await()

            stopSelf()
            notificationManager.notify(NotificationManager.BACKUP_FINISH_NOTIFICATION_ID, notificationManager.createBackupNotification(false))
        }

        return super.onStartCommand(intent, flags, startId)
    }
}