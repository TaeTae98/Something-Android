package com.taetae98.something.di

import android.content.Context
import com.taetae98.something.dao.DrawerDao
import com.taetae98.something.dao.ToDoDao
import com.taetae98.something.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideToDoDao(appDatabase: AppDatabase): ToDoDao {
        return appDatabase.todo()
    }

    @Provides
    fun provideDrawerDao(appDatabase: AppDatabase): DrawerDao {
        return appDatabase.drawer()
    }
}