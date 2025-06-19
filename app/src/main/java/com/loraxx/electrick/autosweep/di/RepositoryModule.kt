package com.loraxx.electrick.autosweep.di

import com.loraxx.electrick.autosweep.data.repository.LoginRepositoryImpl
import com.loraxx.electrick.autosweep.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}
