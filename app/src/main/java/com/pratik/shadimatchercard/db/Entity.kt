package com.pratik.shadimatchercard.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(
    @field:PrimaryKey
    val id: String,
    val result: String
)