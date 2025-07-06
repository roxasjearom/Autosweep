package com.loraxx.electrick.autosweep.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.domain.repository.LoginRepository
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _registrationUiState = MutableStateFlow(RegistrationUiState())
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

    fun updateSelectedIndex(index: Int) {
        _loginUiState.update { currentState ->
            currentState.copy(
                selectedIndex = index,
            )
        }
    }

    fun login(email: String, password: String) {
        if (isCredentialsValid()) {
            viewModelScope.launch {
                _loginUiState.update { currentState ->
                    currentState.copy(isLoading = true)
                }
                val loginResult = loginRepository.login(email, password)
                _loginUiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        loginResult = loginResult,
                    )
                }
            }
        }
    }

    private fun isCredentialsValid(): Boolean {
        return _loginUiState.value.emailField.validationState() == ValidationState.VALID
                && _loginUiState.value.passwordField.validationState() == ValidationState.VALID
    }

    fun onLoginResultConsumed() {
        _loginUiState.update { currentState ->
            currentState.copy(
                loginResult = null,
            )
        }
    }

    fun register(accountNumber: String, plateNumber: String) {
        if (isRegistrationValid()) {
            viewModelScope.launch {
                _registrationUiState.update { currentState ->
                    currentState.copy(isLoading = true)
                }
                val registrationResult = loginRepository.register(accountNumber, plateNumber)
                _registrationUiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        registrationResult = registrationResult,
                    )
                }
            }
        }
    }

    fun onRegistrationResultConsumed() {
        _registrationUiState.update { currentState ->
            currentState.copy(
                registrationResult = null,
            )
        }
    }

    private fun isRegistrationValid(): Boolean {
        return _registrationUiState.value.accountNumberField.validationState() == ValidationState.VALID &&
                _registrationUiState.value.plateNumberField.validationState() == ValidationState.VALID
    }
}
