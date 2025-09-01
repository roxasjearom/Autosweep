package com.loraxx.electrick.autosweep.domain.model

data class AccountDetails(
    val accountNumber: Int,
    val plateNumber: String,
    val accountBalance: Double = 0.0,
)
