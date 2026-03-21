package com.example.myapp.domain.auth.repository

import com.example.myapp.data.user.model.User

interface AuthRepository {
    suspend fun authenticate(login: String, password: String): Result<User>
    suspend fun register(login: String, password: String, email: String? = null): Result<User>
    suspend fun isLoginTaken(login: String): Boolean
}