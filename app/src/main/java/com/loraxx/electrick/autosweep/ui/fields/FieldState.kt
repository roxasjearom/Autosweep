package com.loraxx.electrick.autosweep.ui.fields

import android.util.Patterns
import androidx.compose.foundation.text.input.TextFieldState

enum class ValidationState {
    INITIAL, EMPTY, INVALID, VALID,
}

data class InputFieldState(
    val textFieldState: TextFieldState = TextFieldState(),
    val validationState: ValidationState = ValidationState.INITIAL,
)

typealias StateValidator = (input: String, validateIfEmpty: Boolean) -> ValidationState

val emailStateValidator: StateValidator = { email, validateIfEmpty ->
    when {
        email.isEmpty() && !validateIfEmpty -> ValidationState.INITIAL
        email.isEmpty() && validateIfEmpty -> ValidationState.EMPTY
        (Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> ValidationState.VALID
        else -> ValidationState.INVALID
    }
}

val passwordStateValidator: StateValidator = { password, validateIfEmpty ->
    when {
        password.isEmpty() && !validateIfEmpty -> ValidationState.INITIAL
        password.isEmpty() && validateIfEmpty -> ValidationState.EMPTY
        else -> ValidationState.VALID
    }
}
