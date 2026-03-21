package com.example.myapp.data.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val login: String,
    val password: String,
    val email: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)