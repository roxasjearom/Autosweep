package com.loraxx.electrick.autosweep.ui.login

import com.loraxx.electrick.autosweep.ui.fields.InputFieldState

data class LoginUiState(
    val emailField: InputFieldState = InputFieldState(),
    val passwordField: InputFieldState = InputFieldState(),
    val selectedIndex: Int = 0,
)

data class RegistrationUiState(
    val accountNumberField: InputFieldState = InputFieldState(),
    val plateNumberField: InputFieldState = InputFieldState(),
)
