package com.taetae98.something.repository

import androidx.lifecycle.LiveData
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo
import com.taetae98.something.room.ToDoDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepository @Inject constructor(
    private val todoDao: ToDoDao
) {
    suspend fun insert(todo: ToDo): Long {
        return todoDao.insert(todo)
    }

    suspend fun delete(todo: ToDo) {
        todoDao.delete(todo)
    }

    suspend fun update(todo: ToDo) {
        todoDao.update(todo)
    }

    fun findByDrawerIdLiveData(drawerId: Long): LiveData<List<ToDo>> {
        return todoDao.findByDrawerIdLiveData(drawerId)
    }

    fun findInToDoFragmentLiveData(): LiveData<List<ToDo>> {
        return todoDao.findInToDoFragmentLiveData()
    }

    fun findInCalendarDayDialogLiveData(date: Date): LiveData<List<ToDo>> {
        return todoDao.findInCalendarDayDialogLiveData(date)
    }

    fun findIsNotFinishedAndIsNotificationLiveData(): LiveData<List<ToDo>> {
        return todoDao.findIsNotFinishedAndIsNotificationLiveData()
    }

    fun findInCalendarFragmentLiveData(): LiveData<List<ToDo>> {
        return todoDao.findInCalendarFragmentLiveData()
    }

    fun findIsFinishedLiveData(): LiveData<List<ToDo>> {
        return todoDao.findIsFinishedLiveData()
    }
}