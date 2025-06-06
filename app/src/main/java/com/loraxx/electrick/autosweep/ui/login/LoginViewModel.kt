package com.loraxx.electrick.autosweep.ui.login

import android.util.Patterns
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _emailUiState = MutableStateFlow(EmailUiState())
    val emailUiState: StateFlow<EmailUiState> = _emailUiState.asStateFlow()

    val emailTextFieldState = TextFieldState()
    val passwordTextFieldState = TextFieldState()

    init {
        viewModelScope.launch {
            snapshotFlow { emailTextFieldState.text }.collect { email ->

                _emailUiState.update { currentState ->
                    currentState.copy(
                        hasError = emailHasError(email.toString()),
                        errorMessage = "Email is invalid"
                    )
                }
            }
        }
    }

    private fun emailHasError(email: String): Boolean {
        return if (email.isNotEmpty()) {
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }
}
