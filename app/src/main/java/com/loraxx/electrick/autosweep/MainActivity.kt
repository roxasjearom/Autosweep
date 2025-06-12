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
                val emailInputFieldState by loginViewModel.emailInputFieldState.collectAsStateWithLifecycle()
                val passwordInputFieldState by loginViewModel.passwordInputFieldState.collectAsStateWithLifecycle()
                LoginScreen(
                    emailInputFieldState = emailInputFieldState,
                    passwordInputFieldState = passwordInputFieldState,
                    onLoginClicked = { email, password ->
                        loginViewModel.validateCredentials(email, password)
                    },
                    onForgotPasswordClicked = {
                        //TODO implement forgot password here
                    },
                    onQuickBalanceClicked = {
                        //TODO implement quick balance here
                    }
                )
            }
        }
    }
}
