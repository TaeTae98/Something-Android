package com.taetae98.something

import android.content.res.Resources

const val TAG = "Something"
const val DATABASE_NAME = "something.db"

fun Int.toDp(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5F).toInt()
}