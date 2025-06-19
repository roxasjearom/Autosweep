package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.LoginResult

interface LoginRepository {

    fun login(email: String, password: String): LoginResult
}
