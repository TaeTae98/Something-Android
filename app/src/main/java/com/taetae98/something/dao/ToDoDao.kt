package com.taetae98.something.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.something.base.BaseDao
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo

@Dao
interface ToDoDao : BaseDao<ToDo> {
    companion object {
        private const val ORDER = "ORDER BY isFinished, isOnTop DESC, beginDate, endDate"
    }

    @Query("SELECT * FROM ToDo")
    suspend fun findAll(): List<ToDo>

    @Query("SELECT * FROM ToDo WHERE drawerId=:drawerId $ORDER")
    fun findByDrawerIdLiveData(drawerId: Long): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE drawerId=:drawerId AND isFinished = 0 $ORDER")
    fun findByDrawerIdAndNotFinishedLiveData(drawerId: Long): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo LEFT OUTER JOIN Drawer ON ToDo.drawerId = Drawer.drawerId WHERE isFinished = 0 AND (isVisibleInToDoFragment = 1 OR isVisibleInToDoFragment IS NULL) $ORDER")
    fun findInToDoFragmentLiveData(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo LEFT OUTER JOIN Drawer ON ToDo.drawerId = Drawer.drawerId WHERE beginDate IS NOT NULL AND endDate IS NOT NULL AND (isVisibleInCalendarFragment = 1 OR isVisibleInCalendarFragment IS NULL) ORDER BY beginDate, isFinished, isOnTop DESC, endDate")
    fun findInCalendarFragmentLiveData(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo LEFT OUTER JOIN Drawer ON ToDo.drawerId = Drawer.drawerId WHERE isFinished = 0 AND beginDate IS NOT NULL AND endDate IS NOT NULL AND (isVisibleInCalendarFragment = 1 OR isVisibleInCalendarFragment IS NULL) ORDER BY beginDate, isFinished, isOnTop DESC, endDate")
    fun findByNotFinishedInCalendarFragmentLiveData(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo LEFT OUTER JOIN Drawer ON ToDo.drawerId = Drawer.drawerId WHERE (beginDate <= :date AND :date <= endDate) AND (isVisibleInCalendarFragment = 1 OR isVisibleInCalendarFragment IS NULL) $ORDER")
    fun findInCalendarDayDialogLiveData(date: Date): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo LEFT OUTER JOIN Drawer ON ToDo.drawerId = Drawer.drawerId WHERE isFinished = 0 AND (beginDate <= :date AND :date <= endDate) AND (isVisibleInCalendarFragment = 1 OR isVisibleInCalendarFragment IS NULL) $ORDER")
    fun findByNotFinishedInCalendarDayDialogLiveData(date: Date): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE isFinished = 0 AND isNotification = 1")
    fun findIsNotFinishedAndIsNotificationLiveData(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE isFinished = 1 $ORDER")
    fun findIsFinishedLiveData(): LiveData<List<ToDo>>

    @Query("DELETE FROM ToDo")
    suspend fun deleteAll()
}