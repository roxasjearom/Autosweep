package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@Composable
fun RegistrationSection(
    modifier: Modifier = Modifier,
    accountNumberInputFieldState: InputFieldState,
    plateNumberInputFieldState: InputFieldState,
    onRegisterClicked: (accountNumber: String, plateNumber: String) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        AccountNumberTextField(
            modifier = Modifier.fillMaxWidth(),
            accountNumberInputFieldState = accountNumberInputFieldState,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PlateNumberTextField(
            modifier = Modifier.fillMaxWidth(),
            plateNumberInputFieldState = plateNumberInputFieldState,
            onKeyboardActionClicked = {
                onRegisterClicked(
                    accountNumberInputFieldState.textFieldState.text.toString(),
                    plateNumberInputFieldState.textFieldState.text.toString(),
                )
            }
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                onRegisterClicked(
                    accountNumberInputFieldState.textFieldState.text.toString(),
                    plateNumberInputFieldState.textFieldState.text.toString(),
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.button_register),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun AccountNumberTextField(
    modifier: Modifier = Modifier,
    accountNumberInputFieldState: InputFieldState,
) {
    val hasError = accountNumberInputFieldState.validationState != ValidationState.INITIAL &&
            accountNumberInputFieldState.validationState != ValidationState.VALID

    OutlinedTextField(
        state = accountNumberInputFieldState.textFieldState,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        ),
        label = { Text(stringResource(R.string.hint_account_number)) },
        placeholder = { Text(stringResource(R.string.hint_account_number)) },
        shape = RoundedCornerShape(8.dp),
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (accountNumberInputFieldState.validationState) {
                        ValidationState.EMPTY -> stringResource(R.string.error_account_number_empty)
                        ValidationState.INVALID -> stringResource(R.string.error_account_number_invalid)
                        else -> ""
                    },
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    )
}

@Composable
fun PlateNumberTextField(
    modifier: Modifier = Modifier,
    plateNumberInputFieldState: InputFieldState,
    onKeyboardActionClicked: () -> Unit = { },
) {
    val hasError = plateNumberInputFieldState.validationState != ValidationState.INITIAL &&
            plateNumberInputFieldState.validationState != ValidationState.VALID

    OutlinedTextField(
        state = plateNumberInputFieldState.textFieldState,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        ),
        label = { Text(stringResource(R.string.hint_plate_number)) },
        placeholder = { Text(stringResource(R.string.hint_plate_number)) },
        shape = RoundedCornerShape(8.dp),
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (plateNumberInputFieldState.validationState) {
                        ValidationState.EMPTY -> stringResource(R.string.error_account_number_empty)
                        ValidationState.INVALID -> stringResource(R.string.error_account_number_invalid)
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

@Preview
@Composable
fun RegistrationSectionPreview(modifier: Modifier = Modifier) {
    Autosweep20Theme {
        Surface {
            RegistrationSection(
                accountNumberInputFieldState = InputFieldState(),
                plateNumberInputFieldState = InputFieldState(),
                onRegisterClicked = { _, _ -> },
            )
        }
    }
}
