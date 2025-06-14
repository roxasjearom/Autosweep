package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.ui.fields.emailStateValidator
import com.loraxx.electrick.autosweep.ui.fields.passwordStateValidator
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
                                validationState = passwordStateValidator(password.toString(), false),
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
}
