package com.loraxx.electrick.autosweep.ui.quickbalance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.domain.model.BalanceResult
import com.loraxx.electrick.autosweep.domain.repository.BalanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickBalanceViewModel @Inject constructor(
    private val balanceRepository: BalanceRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(QuickBalanceUiState())
    val uiState: StateFlow<QuickBalanceUiState> = _uiState.asStateFlow()

    fun checkBalance(accountNumber: String) {
        if (isAccountNumberValid()) {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    currentState.copy(isLoading = true)
                }
                when (val balanceResult = balanceRepository.checkBalance(accountNumber)) {
                    is BalanceResult.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                accountBalance = balanceResult.balance,
                            )
                        }
                    }
                    BalanceResult.AccountNumberNotFound -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                accountBalance = 0.0,
                                showInvalidAccountError = true,
                            )
                        }
                    }
                    is BalanceResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onShowInvalidAccountErrorConsumed() {
        _uiState.update { currentState ->
            currentState.copy(showInvalidAccountError = false)
        }
    }

    private fun isAccountNumberValid(): Boolean {
        return _uiState.value.accountNumberField.isValid()
    }
}
