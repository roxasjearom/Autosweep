package com.loraxx.electrick.autosweep.domain.model

data class TollAccountDetails(
    val email: String,
    val accountNumber: String,
    val plateNumber: String,
    val balance: Double,
)
