package com.loraxx.electrick.autosweep.ui.quickbalance

import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator

data class QuickBalanceUiState(
    val accountBalance: Double = 0.0,
    val accountNumberField: InputFieldState = InputFieldState(validator = accountNumberStateValidator),
    val isLoading: Boolean = false,
    val showInvalidAccountError: Boolean = false,
)
