package com.loraxx.electrick.autosweep.domain.model

data class BalanceDetails(
    val plateNumber: String = "-",
    val accountNumber: String = "-",
    val accountBalance: Double = 0.0,
)
