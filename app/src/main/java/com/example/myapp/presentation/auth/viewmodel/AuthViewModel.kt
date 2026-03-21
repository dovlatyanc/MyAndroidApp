package com.example.myapp.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.R
import com.example.myapp.presentation.core.ui.UiText
import com.example.myapp.domain.auth.usecase.AuthenticateUserUseCase
import com.example.myapp.domain.auth.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()  // Убрал message, так как он не используется
    object RegistrationSuccess : AuthUiState()
    data class Error(val message: UiText) : AuthUiState()
}

class AuthViewModel(
    private val authenticateUseCase: AuthenticateUserUseCase,
    private val registerUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _uiState.update { AuthUiState.Loading }

            val result = authenticateUseCase(login, password)

            _uiState.update {
                if (result.isSuccess) {
                    AuthUiState.Success
                } else {
                    AuthUiState.Error(
                        UiText.StringResource(R.string.auth_error_invalid_credentials)
                    )
                }
            }
        }
    }

    fun register(login: String, password: String, email: String?) {
        viewModelScope.launch {
            _uiState.update { AuthUiState.Loading }

            val result = registerUseCase(login, password, email)

            _uiState.update {
                if (result.isSuccess) {
                    AuthUiState.RegistrationSuccess
                } else {
                    val errorMessage = when (result.exceptionOrNull()?.message) {
                        "login_exists" -> UiText.StringResource(R.string.auth_error_login_exists)
                        "weak_password" -> UiText.StringResource(R.string.auth_error_weak_password)
                        else -> UiText.StringResource(R.string.auth_error_registration_failed)
                    }
                    AuthUiState.Error(errorMessage)
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { AuthUiState.Idle }
    }
}