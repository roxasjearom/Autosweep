package com.loraxx.electrick.autosweep.ui.login

import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.model.RegistrationResult
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState

data class LoginUiState(
    val emailField: InputFieldState = InputFieldState(),
    val passwordField: InputFieldState = InputFieldState(),
    val selectedIndex: Int = 0,
    val isLoading: Boolean = false,
    val loginResult: LoginResult? = null,
)

data class RegistrationUiState(
    val accountNumberField: InputFieldState = InputFieldState(),
    val plateNumberField: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
    val registrationResult: RegistrationResult? = null,
)
