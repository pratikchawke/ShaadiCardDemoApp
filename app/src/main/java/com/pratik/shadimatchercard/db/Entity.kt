package com.pratik.shadimatchercard.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(
    @field:PrimaryKey
    val id: Int,
    val name: String,
    val profileUrl: String,
    val status : String,
    val isSelected : Boolean
)