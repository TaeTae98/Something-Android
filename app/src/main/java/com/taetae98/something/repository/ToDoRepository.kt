package com.taetae98.something.repository

import androidx.lifecycle.LiveData
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

    fun findAllLiveDate(): LiveData<List<ToDo>> {
        return todoDao.findAllLiveData()
    }

    fun findByDrawerIdLiveData(drawerId: Long): LiveData<List<ToDo>> {
        return todoDao.findByDrawerIdLiveData(drawerId)
    }
}