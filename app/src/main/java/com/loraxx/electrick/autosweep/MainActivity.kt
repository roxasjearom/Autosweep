package com.loraxx.electrick.autosweep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loraxx.electrick.autosweep.ui.login.LoginScreen
import com.loraxx.electrick.autosweep.ui.login.LoginViewModel
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Autosweep20Theme {
                val loginViewModel: LoginViewModel by viewModels()
                val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
                LoginScreen(
                    emailInputFieldState = loginUiState.emailField,
                    passwordInputFieldState = loginUiState.passwordField,
                    onLoginClicked = { email, password ->
                        loginViewModel.validateCredentials(email, password)
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
                        //TODO implement register here
                    },
                )
            }
        }
    }
}
