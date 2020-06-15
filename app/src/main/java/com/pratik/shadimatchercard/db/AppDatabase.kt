package com.pratik.shadimatchercard.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pratik.shadimatchercard.model.Result

@Database(entities = [Result::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): PersonDao
}