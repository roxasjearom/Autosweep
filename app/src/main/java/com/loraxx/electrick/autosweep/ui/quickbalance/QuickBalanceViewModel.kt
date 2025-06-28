package com.loraxx.electrick.autosweep.ui.quickbalance

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.domain.model.BalanceResult
import com.loraxx.electrick.autosweep.domain.repository.BalanceRepository
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class QuickBalanceViewModel @Inject constructor(
    private val balanceRepository: BalanceRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(QuickBalanceUiState())
    val uiState: StateFlow<QuickBalanceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow { _uiState.value.accountNumberField.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { accountNumber ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            accountNumberField = currentState.accountNumberField.copy(
                                validationState = accountNumberStateValidator(accountNumber.toString(), false),
                            )
                        )
                    }
                }
        }
    }

    fun checkBalance(accountNumber: String) {
        if (isAccountNumberValid(accountNumber)) {
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
                                accountNumberField = currentState.accountNumberField.copy(
                                    validationState = ValidationState.INVALID,
                                )
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

    private fun isAccountNumberValid(accountNumber: String): Boolean {
        _uiState.update { currentState ->
            currentState.copy(
                accountNumberField = currentState.accountNumberField.copy(
                    validationState = accountNumberStateValidator(accountNumber, true),
                )
            )
        }
        return _uiState.value.accountNumberField.validationState == ValidationState.VALID
    }
}
