package com.loraxx.electrick.autosweep.di

import com.loraxx.electrick.autosweep.data.repository.BalanceRepositoryImpl
import com.loraxx.electrick.autosweep.data.repository.DashboardRepositoryImpl
import com.loraxx.electrick.autosweep.data.repository.LoginRepositoryImpl
import com.loraxx.electrick.autosweep.domain.repository.BalanceRepository
import com.loraxx.electrick.autosweep.domain.repository.DashboardRepository
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

    @Binds
    abstract fun bindBalanceRepository(balanceRepositoryImpl: BalanceRepositoryImpl): BalanceRepository

    @Binds
    abstract fun bindDashboardRepository(dashboardRepositoryImpl: DashboardRepositoryImpl): DashboardRepository
}
