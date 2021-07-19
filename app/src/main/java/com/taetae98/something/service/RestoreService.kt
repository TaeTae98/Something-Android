package com.taetae98.something.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.taetae98.something.dto.Drawer
import com.taetae98.something.dto.ToDo
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
class RestoreService : Service() {
    @Inject
    lateinit var todoRepository: ToDoRepository

    @Inject
    lateinit var drawerRepository: DrawerRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NotificationManager.RESTORE_IN_PROGRESS_NOTIFICATION_ID, notificationManager.createRestoreNotification())
        CoroutineScope(Dispatchers.IO).launch {
            val id = FirebaseAuth.getInstance().currentUser!!.uid

            todoRepository.deleteAll()
            drawerRepository.deleteAll()

            val drawerJob = FirebaseDatabase.getInstance().getReference(id).child("drawer").get().await()
            drawerJob.getValue(object : GenericTypeIndicator<List<Drawer>>() {})?.let {
                drawerRepository.insert(it)
            }

            val todoJob = FirebaseDatabase.getInstance().getReference(id).child("todo").get().await()
            todoJob.getValue(object : GenericTypeIndicator<List<ToDo>>() {})?.let {
                todoRepository.insert(it)
            }

            stopSelf()
            notificationManager.notify(NotificationManager.RESTORE_FINISH_NOTIFICATION_ID, notificationManager.createRestoreNotification(false))
        }

        return super.onStartCommand(intent, flags, startId)
    }
}