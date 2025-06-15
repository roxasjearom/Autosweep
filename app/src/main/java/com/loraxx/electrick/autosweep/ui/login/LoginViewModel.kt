package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator
import com.loraxx.electrick.autosweep.ui.fields.emailStateValidator
import com.loraxx.electrick.autosweep.ui.fields.passwordStateValidator
import com.loraxx.electrick.autosweep.ui.fields.plateNumberStateValidator
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
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _registrationUiState = MutableStateFlow(RegistrationUiState())
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow { _loginUiState.value.emailField.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { email ->
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            emailField = currentState.emailField.copy(
                                validationState = emailStateValidator(email.toString(), false),
                            )
                        )
                    }
                }
        }
        viewModelScope.launch {
            snapshotFlow { _loginUiState.value.passwordField.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { password ->
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            passwordField = currentState.passwordField.copy(
                                validationState = passwordStateValidator(
                                    password.toString(),
                                    false,
                                ),
                            )
                        )
                    }
                }
        }
        viewModelScope.launch {
            snapshotFlow { _registrationUiState.value.accountNumberField.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { accountNumber ->
                    _registrationUiState.update { currentState ->
                        currentState.copy(
                            accountNumberField = currentState.accountNumberField.copy(
                                validationState = accountNumberStateValidator(
                                    accountNumber.toString(),
                                    false,
                                ),
                            )
                        )
                    }
                }
        }
        viewModelScope.launch {
            snapshotFlow { _registrationUiState.value.plateNumberField.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { plateNumber ->
                    _registrationUiState.update { currentState ->
                        currentState.copy(
                            plateNumberField = currentState.plateNumberField.copy(
                                validationState = plateNumberStateValidator(
                                    plateNumber.toString(),
                                    false,
                                )
                            )
                        )
                    }
                }
        }
    }

    fun updateSelectedIndex(index: Int) {
        _loginUiState.update { currentState ->
            currentState.copy(
                selectedIndex = index,
            )
        }
    }

    fun validateCredentials(email: String, password: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                emailField = currentState.emailField.copy(
                    validationState = emailStateValidator(email, true),
                )
            )
        }
        _loginUiState.update { currentState ->
            currentState.copy(
                passwordField = currentState.passwordField.copy(
                    validationState = passwordStateValidator(password, true),
                )
            )
        }
    }

    fun validateRegistration(accountNumber: String, plateNumber: String) {
        _registrationUiState.update { currentState ->
            currentState.copy(
                accountNumberField = currentState.accountNumberField.copy(
                    validationState = accountNumberStateValidator(accountNumber, true),
                )
            )
        }
        _registrationUiState.update { currentState ->
            currentState.copy(
                plateNumberField = currentState.plateNumberField.copy(
                    validationState = plateNumberStateValidator(plateNumber, true),
                )
            )
        }
    }

}
