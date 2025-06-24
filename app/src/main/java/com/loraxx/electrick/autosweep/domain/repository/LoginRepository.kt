package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.model.RegistrationResult

interface LoginRepository {

    fun login(email: String, password: String): LoginResult

    fun register(accountNumber: String, plateNumber: String): RegistrationResult
}
