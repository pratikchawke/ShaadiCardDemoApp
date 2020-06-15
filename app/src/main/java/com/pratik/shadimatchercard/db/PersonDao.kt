package com.pratik.shadimatchercard.db

import androidx.room.*
import com.pratik.shadimatchercard.model.Result

@Dao
interface PersonDao {
    @get:Query("SELECT * FROM Entity")
    val entityList: List<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: Result)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg entity: Result)
}