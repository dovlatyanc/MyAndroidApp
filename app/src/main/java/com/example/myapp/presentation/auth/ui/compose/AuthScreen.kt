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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.presentation.auth.viewmodel.AuthUiState
import com.example.myapp.presentation.core.ui.asString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AuthScreen(
    uiState: StateFlow<AuthUiState>,
    onLogin: (String, String) -> Unit,
    onRegisterClick: () -> Unit
) {
    val context = LocalContext.current
    val currentState by uiState.collectAsState()
    var login by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(currentState) {
        when (currentState) {
            is AuthUiState.Success -> {
                Toast.makeText(context, R.string.auth_success_default, Toast.LENGTH_SHORT).show()
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
            text = stringResource(R.string.auth_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text(stringResource(R.string.auth_login_hint)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = currentState is AuthUiState.Error && login.text.isEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.auth_password_hint)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = currentState is AuthUiState.Error && password.text.isEmpty()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onLogin(login.text, password.text) },
            modifier = Modifier.fillMaxWidth(),
            enabled = login.text.isNotEmpty() && password.text.isNotEmpty()
        ) {
            Text(stringResource(R.string.auth_login_button))
        }

        TextButton(onClick = onRegisterClick) {
            Text(stringResource(R.string.auth_register_button))
        }

        if (currentState is AuthUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
        }
    }
}

@Preview(name = "Idle State", showBackground = true)
@Composable
fun AuthScreenIdlePreview() {
    MaterialTheme {
        AuthScreen(
            uiState = MutableStateFlow(AuthUiState.Idle),
            onLogin = { _, _ -> },
            onRegisterClick = {}
        )
    }
}

@Preview(name = "Loading State", showBackground = true)
@Composable
fun AuthScreenLoadingPreview() {
    MaterialTheme {
        AuthScreen(
            uiState = MutableStateFlow(AuthUiState.Loading),
            onLogin = { _, _ -> },
            onRegisterClick = {}
        )
    }
}

@Preview(name = "Error State", showBackground = true)
@Composable
fun AuthScreenErrorPreview() {
    MaterialTheme {
        AuthScreen(
            uiState = MutableStateFlow(
                AuthUiState.Error(
                    com.example.myapp.presentation.core.ui.UiText.StringResource(R.string.auth_error_invalid_credentials)
                )
            ),
            onLogin = { _, _ -> },
            onRegisterClick = {}
        )
    }
}

@Preview(name = "Dark Theme", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AuthScreenDarkPreview() {
    MaterialTheme {
        AuthScreen(
            uiState = MutableStateFlow(AuthUiState.Idle),
            onLogin = { _, _ -> },
            onRegisterClick = {}
        )
    }
}

// Пример с PreviewParameter для разных состояний
class AuthUiStateProvider : PreviewParameterProvider<AuthUiState> {
    override val values = sequenceOf(
        AuthUiState.Idle,
        AuthUiState.Loading,
        AuthUiState.Error(
            com.example.myapp.presentation.core.ui.UiText.StringResource(R.string.auth_error_invalid_credentials)
        ),
        AuthUiState.Success
    )
}

@Preview(name = "All States", showBackground = true)
@Composable
fun AuthScreenAllStatesPreview(
    @PreviewParameter(AuthUiStateProvider::class) state: AuthUiState
) {
    MaterialTheme {
        AuthScreen(
            uiState = MutableStateFlow(state),
            onLogin = { _, _ -> },
            onRegisterClick = {}
        )
    }
}