package com.pratik.shadimatchercard.db

import androidx.room.*
import java.util.*

@Dao
interface PersonDao {
    @get:Query("SELECT * FROM Entity")
    val entityList: List<Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: Entity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg entity: Entity)
}