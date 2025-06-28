package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.domain.repository.LoginRepository
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.StateValidator
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator
import com.loraxx.electrick.autosweep.ui.fields.emailStateValidator
import com.loraxx.electrick.autosweep.ui.fields.passwordStateValidator
import com.loraxx.electrick.autosweep.ui.fields.plateNumberStateValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _registrationUiState = MutableStateFlow(RegistrationUiState())
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

    init {
        viewModelScope.launchValidationFlow(
            uiState = _loginUiState,
            fieldSelector = { state -> state.emailField },
            validator = emailStateValidator,
            updateFieldState = { uiState, updatedField ->
                uiState.copy(emailField = updatedField)
            },
        )

        viewModelScope.launchValidationFlow(
            uiState = _loginUiState,
            fieldSelector = { state -> state.passwordField },
            validator = passwordStateValidator,
            updateFieldState = { uiState, updatedField ->
                uiState.copy(passwordField = updatedField)
            },
        )

        viewModelScope.launchValidationFlow(
            uiState = _registrationUiState,
            fieldSelector = { state -> state.accountNumberField },
            validator = accountNumberStateValidator,
            updateFieldState = { uiState, updatedField ->
                uiState.copy(accountNumberField = updatedField)
            },
        )

        viewModelScope.launchValidationFlow(
            uiState = _registrationUiState,
            fieldSelector = { state -> state.plateNumberField },
            validator = plateNumberStateValidator,
            updateFieldState = { uiState, updatedField ->
                uiState.copy(plateNumberField = updatedField)
            },
        )
    }

    fun updateSelectedIndex(index: Int) {
        _loginUiState.update { currentState ->
            currentState.copy(
                selectedIndex = index,
            )
        }
    }

    fun login(email: String, password: String) {
        if (isCredentialsValid(email, password)) {
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

    private fun isCredentialsValid(email: String, password: String): Boolean {
        //Trigger the validation first then return the result based on the updated field states
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

        return _loginUiState.value.emailField.validationState == ValidationState.VALID
                && _loginUiState.value.passwordField.validationState == ValidationState.VALID
    }

    fun onLoginResultConsumed() {
        _loginUiState.update { currentState ->
            currentState.copy(
                loginResult = null,
            )
        }
    }

    fun register(accountNumber: String, plateNumber: String) {
        if (isRegistrationValid(accountNumber, plateNumber)) {
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

    private fun isRegistrationValid(accountNumber: String, plateNumber: String): Boolean {
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

        return _registrationUiState.value.accountNumberField.validationState == ValidationState.VALID &&
                _registrationUiState.value.plateNumberField.validationState == ValidationState.VALID
    }

    private fun <UI_STATE> CoroutineScope.launchValidationFlow(
        uiState: MutableStateFlow<UI_STATE>,
        fieldSelector: (UI_STATE) -> InputFieldState,
        validator: StateValidator,
        updateFieldState: (UI_STATE, InputFieldState) -> UI_STATE,
    ) {
        this.launch {
            snapshotFlow { fieldSelector(uiState.value).textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged()
                .collect { text ->
                    uiState.update { uiState ->
                        val currentField = fieldSelector(uiState)
                        updateFieldState(
                            uiState,
                            currentField.copy(
                                validationState = validator(text.toString(), false),
                            )
                        )
                    }
                }
        }
    }
}
