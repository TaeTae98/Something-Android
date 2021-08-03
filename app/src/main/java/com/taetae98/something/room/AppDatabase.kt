package com.taetae98.something.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taetae98.something.DATABASE_NAME
import com.taetae98.something.dao.DrawerDao
import com.taetae98.something.dao.ToDoDao
import com.taetae98.something.dto.Drawer
import com.taetae98.something.dto.ToDo

@Database(entities = [ToDo::class, Drawer::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addMigrations(
                        migration_1_2
                    )
                    .build()
                    .also {
                        instance = it
                    }
            }
        }

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ToDo ADD COLUMN password TEXT DEFAULT null")
                database.execSQL("ALTER TABLE ToDo ADD COLUMN hideDescription INTEGER NOT NULL DEFAULT 0")
            }
        }
    }

    abstract fun todo(): ToDoDao
    abstract fun drawer(): DrawerDao
}