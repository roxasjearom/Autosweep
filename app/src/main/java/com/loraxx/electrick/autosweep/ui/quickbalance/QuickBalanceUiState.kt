package com.loraxx.electrick.autosweep.ui.quickbalance

import com.loraxx.electrick.autosweep.ui.fields.InputFieldState

data class QuickBalanceUiState(
    val accountBalance: Double = 0.0,
    val accountNumberField: InputFieldState = InputFieldState(),
    val isLoading: Boolean = false,
)
