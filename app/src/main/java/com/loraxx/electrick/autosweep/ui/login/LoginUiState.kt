package com.loraxx.electrick.autosweep.ui.login

import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.model.RegistrationResult
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator
import com.loraxx.electrick.autosweep.ui.fields.emailStateValidator
import com.loraxx.electrick.autosweep.ui.fields.passwordStateValidator
import com.loraxx.electrick.autosweep.ui.fields.plateNumberStateValidator

data class LoginUiState(
    val emailField: InputFieldState = InputFieldState(validator = emailStateValidator),
    val passwordField: InputFieldState = InputFieldState(validator = passwordStateValidator),
    val selectedIndex: Int = 0,
    val isLoading: Boolean = false,
    val loginResult: LoginResult? = null,
)

data class RegistrationUiState(
    val accountNumberField: InputFieldState = InputFieldState(validator = accountNumberStateValidator),
    val plateNumberField: InputFieldState = InputFieldState(validator = plateNumberStateValidator),
    val isLoading: Boolean = false,
    val registrationResult: RegistrationResult? = null,
)
