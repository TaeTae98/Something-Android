package com.taetae98.something.base

import androidx.room.*

@Dao
interface BaseDao<E: Any> {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(elements: E): Long

    @Transaction
    @Delete
    suspend fun delete(elements: E)

    @Transaction
    @Update
    suspend fun update(elements: E)
}