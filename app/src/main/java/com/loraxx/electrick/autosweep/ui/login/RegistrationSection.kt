package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.allCaps
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.then
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.DigitOnlyInputTransformation
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.NoSpaceInputTransformation
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator
import com.loraxx.electrick.autosweep.ui.fields.plateNumberStateValidator
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@Composable
fun RegistrationSection(
    modifier: Modifier = Modifier,
    accountNumberInputFieldState: InputFieldState,
    plateNumberInputFieldState: InputFieldState,
    onRegisterClicked: (accountNumber: String, plateNumber: String) -> Unit,
) {
    val areInputsValid =
        accountNumberInputFieldState.isValid() && plateNumberInputFieldState.isValid()

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
            onKeyboardActionClick = {
                onRegisterClicked(
                    accountNumberInputFieldState.textFieldState.text.toString(),
                    plateNumberInputFieldState.textFieldState.text.toString(),
                )
            }
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            enabled = areInputsValid,
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
    imeAction: ImeAction = ImeAction.Next,
    onKeyboardActionClick: () -> Unit = { },
) {
    val hasError = accountNumberInputFieldState.getValidationState() == ValidationState.INVALID

    OutlinedTextField(
        state = accountNumberInputFieldState.textFieldState,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
        ),
        onKeyboardAction = { performDefaultAction ->
            onKeyboardActionClick()
            performDefaultAction()
        },
        inputTransformation = InputTransformation
            .maxLength(7)
            .then(DigitOnlyInputTransformation()),
        label = { Text(stringResource(R.string.hint_account_number)) },
        placeholder = { Text(stringResource(R.string.hint_account_number)) },
        shape = RoundedCornerShape(8.dp),
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (accountNumberInputFieldState.getValidationState()) {
                        ValidationState.INVALID -> stringResource(R.string.error_account_number_invalid)
                        ValidationState.VALID, ValidationState.INITIAL -> ""
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
    imeAction: ImeAction = ImeAction.Done,
    onKeyboardActionClick: () -> Unit = { },
) {
    val hasError = plateNumberInputFieldState.getValidationState() == ValidationState.INVALID

    OutlinedTextField(
        state = plateNumberInputFieldState.textFieldState,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
            capitalization = KeyboardCapitalization.Characters,
        ),
        inputTransformation = InputTransformation
            .maxLength(7)
            .allCaps(Locale.current)
            .then(NoSpaceInputTransformation()),
        label = { Text(stringResource(R.string.hint_plate_number)) },
        placeholder = { Text(stringResource(R.string.hint_format_plate_number)) },
        shape = RoundedCornerShape(8.dp),
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (plateNumberInputFieldState.getValidationState()) {
                        ValidationState.INVALID -> stringResource(R.string.error_plate_number_invalid)
                        ValidationState.VALID, ValidationState.INITIAL -> ""
                    },
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        onKeyboardAction = { performDefaultAction ->
            onKeyboardActionClick()
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
                accountNumberInputFieldState = InputFieldState(validator = accountNumberStateValidator),
                plateNumberInputFieldState = InputFieldState(validator = plateNumberStateValidator),
                onRegisterClicked = { _, _ -> },
            )
        }
    }
}
