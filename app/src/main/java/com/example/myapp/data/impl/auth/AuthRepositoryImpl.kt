package com.example.myapp.data.impl.auth

import com.example.myapp.data.user.dao.UserDao
import com.example.myapp.data.user.model.User
import com.example.myapp.domain.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun authenticate(login: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val user = userDao.findByLogin(login)
            if (user != null && user.password == password) {
                Result.success(user)
            } else {
                Result.failure(Exception("Неверный логин или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(login: String, password: String, email: String?): Result<User> = withContext(Dispatchers.IO) {
        try {
            val existingUser = userDao.findByLogin(login)
            if (existingUser != null) {
                return@withContext Result.failure(Exception("Пользователь с таким логином уже существует"))
            }

            val user = User(
                login = login,
                password = password,
                email = email
            )

            val id = userDao.insert(user)
            Result.success(user.copy(id = id.toInt()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isLoginTaken(login: String): Boolean = withContext(Dispatchers.IO) {
        userDao.existsByLogin(login)
    }
}