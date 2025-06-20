package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.data.repository.FakeData.ADMIN_EMAIL
import com.loraxx.electrick.autosweep.data.repository.FakeData.ADMIN_PASSWORD
import com.loraxx.electrick.autosweep.domain.model.LoginResult
import com.loraxx.electrick.autosweep.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override fun login(email: String, password: String): LoginResult {
        return when {
            email == ADMIN_EMAIL && password == ADMIN_PASSWORD -> LoginResult.Success
            else -> LoginResult.InvalidCredentials
        }
    }
}
