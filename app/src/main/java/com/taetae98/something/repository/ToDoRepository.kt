package com.taetae98.something.repository

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
}