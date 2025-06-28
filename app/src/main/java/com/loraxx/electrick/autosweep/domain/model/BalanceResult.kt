package com.loraxx.electrick.autosweep.domain.model

sealed class BalanceResult {
    data class Success(val balance: Double) : BalanceResult()
    data object AccountNumberNotFound : BalanceResult()
    data class Error(val message: String) : BalanceResult()
}
