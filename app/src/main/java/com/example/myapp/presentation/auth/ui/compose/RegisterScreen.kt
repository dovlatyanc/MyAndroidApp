package com.example.myapp.presentation.auth.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.presentation.auth.viewmodel.AuthUiState
import com.example.myapp.presentation.core.ui.asString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterScreen(
    uiState: StateFlow<AuthUiState>,
    onRegister: (String, String, String?) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val currentState by uiState.collectAsState()
    var login by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(currentState) {
        when (currentState) {
            is AuthUiState.RegistrationSuccess -> {
                Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show()
            }
            is AuthUiState.Error -> {
                val errorMessage = (currentState as AuthUiState.Error).message.asString(context)
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text(stringResource(R.string.register_login_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.register_email_hint)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.register_password_hint)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Text(
            text = stringResource(R.string.register_password_hint_text),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onRegister(login.text, password.text, email.text.takeIf { it.isNotEmpty() }) },
            modifier = Modifier.fillMaxWidth(),
            enabled = login.text.isNotEmpty() && password.text.length >= 6
        ) {
            Text(stringResource(R.string.register_button))
        }

        TextButton(onClick = onBackClick) {
            Text(stringResource(R.string.register_back_to_login))
        }

        if (currentState is AuthUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
        }
    }
}

@Preview(name = "Register Idle", showBackground = true)
@Composable
fun RegisterScreenIdlePreview() {
    MaterialTheme {
        RegisterScreen(
            uiState = MutableStateFlow(AuthUiState.Idle),
            onRegister = { _, _, _ -> },
            onBackClick = {}
        )
    }
}

@Preview(name = "Register Loading", showBackground = true)
@Composable
fun RegisterScreenLoadingPreview() {
    MaterialTheme {
        RegisterScreen(
            uiState = MutableStateFlow(AuthUiState.Loading),
            onRegister = { _, _, _ -> },
            onBackClick = {}
        )
    }
}

@Preview(name = "Register Error", showBackground = true)
@Composable
fun RegisterScreenErrorPreview() {
    MaterialTheme {
        RegisterScreen(
            uiState = MutableStateFlow(
                AuthUiState.Error(
                    com.example.myapp.presentation.core.ui.UiText.StringResource(R.string.auth_error_weak_password)
                )
            ),
            onRegister = { _, _, _ -> },
            onBackClick = {}
        )
    }
}