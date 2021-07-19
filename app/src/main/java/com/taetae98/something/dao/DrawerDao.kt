package com.taetae98.something.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.something.base.BaseDao
import com.taetae98.something.dto.Drawer

@Dao
interface DrawerDao : BaseDao<Drawer> {
    @Query("SELECT * FROM Drawer")
    suspend fun findAll(): List<Drawer>

    @Query("SELECT * FROM Drawer")
    fun findAllLiveDate(): LiveData<List<Drawer>>

    @Query("DELETE FROM Drawer")
    suspend fun deleteAll()
}