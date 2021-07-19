package com.taetae98.something.repository

import androidx.lifecycle.LiveData
import com.taetae98.something.dao.DrawerDao
import com.taetae98.something.dto.Drawer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawerRepository @Inject constructor(
    private val drawerDao: DrawerDao
) {
    suspend fun insert(drawer: Drawer): Long {
        return drawerDao.insert(drawer)
    }

    suspend fun insert(list: List<Drawer>): LongArray {
        return drawerDao.insert(list)
    }

    suspend fun delete(drawer: Drawer) {
        drawerDao.delete(drawer)
    }

    suspend fun update(drawer: Drawer) {
        drawerDao.update(drawer)
    }

    suspend fun findAll(): List<Drawer> {
        return drawerDao.findAll()
    }

    fun findAllLiveData(): LiveData<List<Drawer>> {
        return drawerDao.findAllLiveDate()
    }

    suspend fun deleteAll() {
        drawerDao.deleteAll()
    }
}