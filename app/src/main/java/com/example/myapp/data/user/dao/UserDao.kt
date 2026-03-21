package com.example.myapp.data.user.dao

import androidx.room.*
import com.example.myapp.data.user.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insert(user: User): Long

    @Delete
    fun delete(user: User): Int

    @Update
    fun update(user: User): Int

    @Query("SELECT * FROM users WHERE login = :login")
    fun findByLogin(login: String): User?

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE login = :login)")
    fun existsByLogin(login: String): Boolean
}