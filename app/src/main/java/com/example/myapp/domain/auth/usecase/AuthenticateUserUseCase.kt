package com.example.myapp.domain.auth.usecase

import com.example.myapp.data.user.model.User
import com.example.myapp.domain.auth.repository.AuthRepository

class AuthenticateUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(login: String, password: String): Result<User> {
        return repository.authenticate(login, password)
    }
}