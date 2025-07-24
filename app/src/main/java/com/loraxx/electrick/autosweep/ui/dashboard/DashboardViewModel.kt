package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        fetchBalanceDetails()
        fetchTrafficAdvisory()
        fetchNews()
    }

    fun fetchBalanceDetails() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    balanceDetails = BalanceDetails(),
                )
            }
            //TODO update account number once session manager is implemented
            val balanceDetails = dashboardRepository.getBalanceDetails("123456")

            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    balanceDetails = balanceDetails,
                )
            }
        }
    }

    fun fetchTrafficAdvisory() {
        viewModelScope.launch {
            val trafficAdvisory = dashboardRepository.getTrafficAdvisory()

            _uiState.update { currentState ->
                currentState.copy(
                    trafficAdvisory = trafficAdvisory,
                )
            }
        }
    }

    fun fetchNews() {
        viewModelScope.launch {
            val newsItems = dashboardRepository.getNews()
            _uiState.update { currentState ->
                currentState.copy(
                    newsItems = newsItems,
                )
            }
        }
    }
}
