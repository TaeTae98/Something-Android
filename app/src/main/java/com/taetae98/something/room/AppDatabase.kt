package com.taetae98.something.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.taetae98.something.DATABASE_NAME
import com.taetae98.something.dto.Drawer
import com.taetae98.something.dto.ToDo

@Database(entities = [ToDo::class, Drawer::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }

    abstract fun todo(): ToDoDao
    abstract fun drawer(): DrawerDao
}