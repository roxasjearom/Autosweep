package com.loraxx.electrick.autosweep.domain.model

sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
    data object NetworkError : LoginResult()
    data object InvalidCredentials : LoginResult()
}
