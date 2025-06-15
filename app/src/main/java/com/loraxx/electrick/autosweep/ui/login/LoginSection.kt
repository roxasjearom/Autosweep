package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.ValidationState

@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    emailInputFieldState: InputFieldState,
    passwordInputFieldState: InputFieldState,
    onLoginClicked: (email: String, password: String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onQuickBalanceClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            emailInputFieldState = emailInputFieldState,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            passwordInputFieldState = passwordInputFieldState,
            onKeyboardActionClicked = {
                onLoginClicked(
                    emailInputFieldState.textFieldState.text.toString(),
                    passwordInputFieldState.textFieldState.text.toString(),
                )
            }
        )
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onForgotPasswordClicked
        ) {
            Text(
                stringResource(R.string.button_forgot_password),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                onLoginClicked(
                    emailInputFieldState.textFieldState.text.toString(),
                    passwordInputFieldState.textFieldState.text.toString(),
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.button_login),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onQuickBalanceClicked
        ) {
            Text(
                stringResource(R.string.button_quick_balance),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailInputFieldState: InputFieldState,
) {
    val hasError = emailInputFieldState.validationState != ValidationState.INITIAL &&
            emailInputFieldState.validationState != ValidationState.VALID

    OutlinedTextField(
        state = emailInputFieldState.textFieldState,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        label = { Text(stringResource(R.string.hint_email)) },
        placeholder = { Text(stringResource(R.string.hint_email)) },
        shape = RoundedCornerShape(8.dp),
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (emailInputFieldState.validationState) {
                        ValidationState.EMPTY -> stringResource(R.string.error_email_empty)
                        ValidationState.INVALID -> stringResource(R.string.error_email_invalid)
                        else -> ""
                    },
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordInputFieldState: InputFieldState,
    onKeyboardActionClicked: () -> Unit = { },
) {
    val hasError = passwordInputFieldState.validationState != ValidationState.INITIAL &&
            passwordInputFieldState.validationState != ValidationState.VALID
    var showPassword by remember { mutableStateOf(false) }

    OutlinedSecureTextField(
        state = passwordInputFieldState.textFieldState,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        textObfuscationMode =
            if (showPassword) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
        label = { Text(stringResource(R.string.hint_password)) },
        placeholder = { Text(stringResource(R.string.hint_password)) },
        trailingIcon = {
            Icon(
                if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = stringResource(R.string.cd_toggle_password_visibility),
                modifier = Modifier.clickable { showPassword = !showPassword }
            )
        },
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (passwordInputFieldState.validationState) {
                        ValidationState.EMPTY -> stringResource(R.string.error_password_empty)
                        ValidationState.INVALID -> stringResource(R.string.error_password_invalid)
                        else -> ""
                    },
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        onKeyboardAction = { performDefaultAction ->
            onKeyboardActionClicked()
            performDefaultAction()
        },
    )
}