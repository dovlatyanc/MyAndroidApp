@file:Suppress("DEPRECATION")

package com.example.myapp.core.di.auth

import com.example.myapp.data.impl.auth.AuthRepositoryImpl
import com.example.myapp.data.user.database.AppDatabase
import com.example.myapp.domain.auth.repository.AuthRepository
import com.example.myapp.domain.auth.usecase.AuthenticateUserUseCase
import com.example.myapp.domain.auth.usecase.RegisterUserUseCase
import com.example.myapp.presentation.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    single { AppDatabase.getInstance(get()) }

    single { get<AppDatabase>().userDao() }

    single<AuthRepository> { AuthRepositoryImpl(userDao = get()) }

    factory { AuthenticateUserUseCase(repository = get()) }
    factory { RegisterUserUseCase(repository = get()) }

    viewModel { AuthViewModel(authenticateUseCase = get(), registerUseCase = get()) }
}