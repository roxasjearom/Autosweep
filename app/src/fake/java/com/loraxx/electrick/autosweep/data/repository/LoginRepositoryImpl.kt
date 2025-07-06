package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.data.repository.FakeData.ADMIN_PASSWORD
import com.loraxx.electrick.autosweep.data.repository.FakeData.fakeRegistrationData
import com.loraxx.electrick.autosweep.data.repository.FakeData.fakeTollAccountDetails
import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.model.RegistrationResult
import com.loraxx.electrick.autosweep.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override suspend fun login(email: String, password: String): LoginResult {
        return when {
            fakeTollAccountDetails.any { it.email == email && password == ADMIN_PASSWORD } -> LoginResult.Success
            else -> LoginResult.InvalidCredentials
        }
    }

    override suspend fun register(accountNumber: String, plateNumber: String): RegistrationResult {
        return when {
            fakeRegistrationData.none { it.first == accountNumber } -> RegistrationResult.DoesNotExist
            fakeRegistrationData.any { it.first == accountNumber && it.second == plateNumber } -> RegistrationResult.Success
            fakeRegistrationData.any { it.first == accountNumber && it.second != plateNumber } -> RegistrationResult.DoesNotMatch
            else -> RegistrationResult.DoesNotMatch
        }
    }
}
