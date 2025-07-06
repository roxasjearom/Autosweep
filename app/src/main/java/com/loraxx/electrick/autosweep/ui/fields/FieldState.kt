package com.loraxx.electrick.autosweep.ui.fields

import android.util.Patterns
import androidx.compose.foundation.text.input.TextFieldState

enum class ValidationState {
    INITIAL, INVALID, VALID,
}

data class InputFieldState(
    val textFieldState: TextFieldState = TextFieldState(),
    private val validator: StateValidator,
) {
    fun getValidationState(): ValidationState {
        return validator(textFieldState.text.toString())
    }

    fun isValid(): Boolean {
        return getValidationState() == ValidationState.VALID
    }
}

typealias StateValidator = (input: String) -> ValidationState

val emailStateValidator: StateValidator = { email ->
    when {
        email.isEmpty() -> ValidationState.INITIAL
        (Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> ValidationState.VALID
        else -> ValidationState.INVALID
    }
}

val passwordStateValidator: StateValidator = { password ->
    when {
        password.isEmpty() -> ValidationState.INITIAL
        else -> ValidationState.VALID
    }
}

val accountNumberStateValidator: StateValidator = { accountNumber ->
    when {
        accountNumber.isEmpty() -> ValidationState.INITIAL
        !accountNumber.all { it.isDigit() } -> ValidationState.INVALID
        accountNumber.length in 6..7 -> ValidationState.VALID
        else -> ValidationState.INVALID
    }
}

val plateNumberStateValidator: StateValidator = { plateNumber ->
    val fourWheelRegex = Regex("^[A-Z]{3}\\d{4}$")
    when {
        plateNumber.isEmpty() -> ValidationState.INITIAL
        fourWheelRegex.matches(plateNumber) -> ValidationState.VALID
        else -> ValidationState.INVALID
    }
}
