package com.taetae98.something.repository

import androidx.lifecycle.LiveData
import com.taetae98.something.dto.Drawer
import com.taetae98.something.room.DrawerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawerRepository @Inject constructor(
    private val drawerDao: DrawerDao
) {
    suspend fun findAll(): List<Drawer> {
        return drawerDao.findAll()
    }

    suspend fun insert(drawer: Drawer): Long {
        return drawerDao.insert(drawer)
    }

    suspend fun insert(list: List<Drawer>) {
        list.forEach { insert(it) }
    }

    suspend fun delete(drawer: Drawer) {
        drawerDao.delete(drawer)
    }

    suspend fun update(drawer: Drawer) {
        drawerDao.update(drawer)
    }

    suspend fun update(list: List<Drawer>) {
        list.forEach { update(it) }
    }

    fun findAllLiveData(): LiveData<List<Drawer>> {
        return drawerDao.findAllLiveDate()
    }
}