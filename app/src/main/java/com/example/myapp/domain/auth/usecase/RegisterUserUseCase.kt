package com.example.myapp.domain.auth.usecase

import com.example.myapp.data.user.model.User
import com.example.myapp.domain.auth.repository.AuthRepository

class RegisterUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(login: String, password: String, email: String? = null): Result<User> {
        return repository.register(login, password, email)
    }
}