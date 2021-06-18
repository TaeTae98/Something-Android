package com.taetae98.something

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

const val TAG = "Something"
const val DATABASE_NAME = "something.db"

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore("setting")


fun Int.toDp(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5F).toInt()
}