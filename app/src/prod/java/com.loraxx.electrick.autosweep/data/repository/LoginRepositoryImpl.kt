package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.model.RegistrationResult
import com.loraxx.electrick.autosweep.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override suspend fun login(email: String, password: String): LoginResult {
        //TODO Update this once we have a valid API
        return when {
            email == "admin@prod.com" && password == "admin" -> LoginResult.Success
            else -> LoginResult.InvalidCredentials
        }
    }

    override suspend fun register(accountNumber: String, plateNumber: String): RegistrationResult {
        TODO("Not yet implemented")
    }
}
