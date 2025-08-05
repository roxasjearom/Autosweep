package com.loraxx.electrick.autosweep.ui.topup.bank

import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.topUpAmountValidator

data class AmountInputUiState(
    val topUpAmountField: InputFieldState = InputFieldState(validator = topUpAmountValidator),
)
