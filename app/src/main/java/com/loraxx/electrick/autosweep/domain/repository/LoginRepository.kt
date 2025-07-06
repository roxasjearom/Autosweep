package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.model.RegistrationResult

interface LoginRepository {

    suspend fun login(email: String, password: String): LoginResult

    suspend fun register(accountNumber: String, plateNumber: String): RegistrationResult
}
