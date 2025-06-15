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

val accountNumberStateValidator: StateValidator = { accountNumber, validateIfEmpty ->
    when {
        accountNumber.isEmpty() && !validateIfEmpty -> ValidationState.INITIAL
        accountNumber.isEmpty() && validateIfEmpty -> ValidationState.EMPTY
        !accountNumber.all { it.isDigit() } -> ValidationState.INVALID
        accountNumber.length in 6..7 -> ValidationState.VALID
        else -> ValidationState.INVALID
    }
}

val plateNumberStateValidator: StateValidator = { plateNumber, validateIfEmpty ->
    val fourWheelRegex = Regex("^[A-Z]{3}\\d{4}$")
    when {
        plateNumber.isEmpty() && !validateIfEmpty -> ValidationState.INITIAL
        plateNumber.isEmpty() && validateIfEmpty -> ValidationState.EMPTY
        fourWheelRegex.matches(plateNumber) -> ValidationState.VALID
        else -> ValidationState.INVALID
    }
}
