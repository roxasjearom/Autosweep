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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import com.loraxx.electrick.autosweep.ui.fields.emailStateValidator
import com.loraxx.electrick.autosweep.ui.fields.passwordStateValidator
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    emailInputFieldState: InputFieldState,
    passwordInputFieldState: InputFieldState,
    onLoginClicked: (email: String, password: String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onQuickBalanceClicked: () -> Unit,
) {
    val areInputsValid = emailInputFieldState.isValid() && passwordInputFieldState.isValid()

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
            colors = ButtonDefaults.textButtonColors()
                .copy(contentColor = MaterialTheme.colorScheme.secondary),
            onClick = onForgotPasswordClicked
        ) {
            Text(
                text = stringResource(R.string.button_forgot_password),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            )
        }
        Spacer(modifier = Modifier.height(36.dp))

        Button(
            enabled = areInputsValid,
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
            colors = ButtonDefaults.textButtonColors()
                .copy(contentColor = MaterialTheme.colorScheme.secondary),
            onClick = onQuickBalanceClicked,
        ) {
            Text(
                text = stringResource(R.string.button_quick_balance),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailInputFieldState: InputFieldState,
) {
    val hasError = emailInputFieldState.getValidationState() == ValidationState.INVALID

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
                    text = when (emailInputFieldState.getValidationState()) {
                        ValidationState.INVALID -> stringResource(R.string.error_email_invalid)
                        ValidationState.VALID, ValidationState.INITIAL -> ""
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
    val hasError = passwordInputFieldState.getValidationState() == ValidationState.INVALID
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
                    text = when (passwordInputFieldState.getValidationState()) {
                        ValidationState.INVALID -> stringResource(R.string.error_password_invalid)
                        ValidationState.INITIAL, ValidationState.VALID -> ""
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

@Preview
@Composable
fun LoginSectionPreview(modifier: Modifier = Modifier) {
    Autosweep20Theme {
        Surface {
            LoginSection(
                emailInputFieldState = InputFieldState(validator =  emailStateValidator),
                passwordInputFieldState = InputFieldState(validator = passwordStateValidator),
                onLoginClicked = { _, _ -> },
                onQuickBalanceClicked = {},
                onForgotPasswordClicked = {},
            )
        }
    }
}
