package com.taetae98.something.repository

import androidx.lifecycle.LiveData
import com.taetae98.something.dao.ToDoDao
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepository @Inject constructor(
    private val todoDao: ToDoDao
) {
    suspend fun insert(todo: ToDo): Long {
        return todoDao.insert(todo)
    }

    suspend fun insert(list: List<ToDo>): LongArray {
        return todoDao.insert(list)
    }

    suspend fun delete(todo: ToDo) {
        todoDao.delete(todo)
    }

    suspend fun update(todo: ToDo) {
        todoDao.update(todo)
    }

    suspend fun findAll(): List<ToDo> {
        return todoDao.findAll()
    }

    fun findByDrawerIdLiveData(drawerId: Long): LiveData<List<ToDo>> {
        return todoDao.findByDrawerIdLiveData(drawerId)
    }

    fun findByDrawerIdAndNotFinishedLiveData(drawerId: Long): LiveData<List<ToDo>> {
        return todoDao.findByDrawerIdAndNotFinishedLiveData(drawerId)
    }

    fun findInToDoFragmentLiveData(): LiveData<List<ToDo>> {
        return todoDao.findInToDoFragmentLiveData()
    }

    fun findInCalendarFragmentLiveData(): LiveData<List<ToDo>> {
        return todoDao.findInCalendarFragmentLiveData()
    }

    fun findByNotFinishedInCalendarFragmentLiveData(): LiveData<List<ToDo>> {
        return todoDao.findByNotFinishedInCalendarFragmentLiveData()
    }

    fun findInCalendarDayDialogLiveData(date: Date): LiveData<List<ToDo>> {
        return todoDao.findInCalendarDayDialogLiveData(date)
    }

    fun findByNotFinishedInCalendarDayDialogLiveData(date: Date): LiveData<List<ToDo>> {
        return todoDao.findByNotFinishedInCalendarDayDialogLiveData(date)
    }

    fun findIsNotFinishedAndIsNotificationLiveData(): LiveData<List<ToDo>> {
        return todoDao.findIsNotFinishedAndIsNotificationLiveData()
    }

    fun findIsFinishedLiveData(): LiveData<List<ToDo>> {
        return todoDao.findIsFinishedLiveData()
    }
}