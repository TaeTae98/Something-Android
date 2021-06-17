package com.taetae98.something.repository

import com.taetae98.something.dto.Drawer
import com.taetae98.something.room.DrawerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawerRepository @Inject constructor(
    private val drawerDao: DrawerDao
) {
    suspend fun insert(drawer: Drawer): Long {
        return drawerDao.insert(drawer)
    }
}