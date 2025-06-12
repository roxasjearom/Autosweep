package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
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

    private val _emailInputFieldState = MutableStateFlow(InputFieldState())
    val emailInputFieldState: StateFlow<InputFieldState> = _emailInputFieldState.asStateFlow()

    private val _passwordInputFieldState = MutableStateFlow(InputFieldState())
    val passwordInputFieldState: StateFlow<InputFieldState> = _passwordInputFieldState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow { _emailInputFieldState.value.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { email ->
                    _emailInputFieldState.update { currentState ->
                        currentState.copy(
                            validationState = emailStateValidator(email.toString(), false),
                        )
                    }
                }
        }
        viewModelScope.launch {
            snapshotFlow { _passwordInputFieldState.value.textFieldState.text }
                .debounce(300L)
                .distinctUntilChanged().collect { password ->
                    _passwordInputFieldState.update { currentState ->
                        currentState.copy(
                            validationState = passwordStateValidator(password.toString(), false),
                        )
                    }
                }
        }
    }

    fun validateCredentials(email: String, password: String) {
        _emailInputFieldState.update { currentState ->
            currentState.copy(
                validationState = emailStateValidator(email, true),
            )
        }
        _passwordInputFieldState.update { currentState ->
            currentState.copy(
                validationState = passwordStateValidator(password, true),
            )
        }
    }
}
