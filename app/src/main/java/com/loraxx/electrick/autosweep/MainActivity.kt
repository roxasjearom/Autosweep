package com.loraxx.electrick.autosweep

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.ui.login.LoginScreen
import com.loraxx.electrick.autosweep.ui.login.LoginViewModel
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Autosweep20Theme {
                val loginViewModel: LoginViewModel by viewModels()
                val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
                val registrationUiState by loginViewModel.registrationUiState.collectAsStateWithLifecycle()

                LaunchedEffect(loginUiState.loginResult) {
                    when (loginUiState.loginResult) {
                        is LoginResult.Success -> {
                            Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                            //TODO navigate to dashboard
                        }

                        is LoginResult.Error -> {
                            val errorMessage = (loginUiState.loginResult as LoginResult.Error).message
                            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        LoginResult.InvalidCredentials -> {
                            Toast.makeText(this@MainActivity, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                        }
                        LoginResult.NetworkError -> {
                            Toast.makeText(this@MainActivity, "Network error!", Toast.LENGTH_SHORT).show()
                        }
                        null -> {
                            //Do nothing
                        }
                    }
                    loginViewModel.onLoginResultConsumed()
                }

                LoginScreen(
                    emailInputFieldState = loginUiState.emailField,
                    passwordInputFieldState = loginUiState.passwordField,
                    accountNumberInputFieldState = registrationUiState.accountNumberField,
                    plateNumberInputFieldState = registrationUiState.plateNumberField,
                    onLoginClicked = { email, password ->
                        loginViewModel.login(email, password)
                    },
                    onForgotPasswordClicked = {
                        //TODO implement forgot password here
                    },
                    onQuickBalanceClicked = {
                        //TODO implement quick balance here
                    },
                    selectedIndex = loginUiState.selectedIndex,
                    onSelectedIndexChange = {
                        loginViewModel.updateSelectedIndex(it)
                    },
                    onRegisterClicked = { accountNumber, plateNumber ->
                        loginViewModel.validateRegistration(accountNumber, plateNumber)
                    },
                )
            }
        }
    }
}
