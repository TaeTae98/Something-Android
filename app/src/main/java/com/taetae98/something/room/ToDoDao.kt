package com.taetae98.something.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.something.base.BaseDao
import com.taetae98.something.dto.ToDo

@Dao
interface ToDoDao : BaseDao<ToDo> {
    companion object {
        private const val ORDER = "ORDER BY isFinished, isOnTop DESC, beginDate, endDate"
    }

    @Query("SELECT * FROM ToDo $ORDER")
    fun findAllLiveData(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE drawerId=:drawerId $ORDER")
    fun findByDrawerIdLiveData(drawerId: Long): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo LEFT OUTER JOIN Drawer ON ToDo.drawerId = Drawer.drawerId WHERE password IS NULL AND isFinished = 0 AND (isVisibleInToDoFragment = 1 OR isVisibleInToDoFragment IS NULL) $ORDER")
    fun findInToDoFragmentLiveData(): LiveData<List<ToDo>>
}