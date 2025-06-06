package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    emailTextFieldState: TextFieldState,
    emailUiState: EmailUiState,
    passwordTextFieldState: TextFieldState,
    onLoginClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onQuickBalanceClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_revamp_logo),
                    contentDescription = "Autosweep log",
                    modifier = Modifier.width(100.dp),
                    contentScale = ContentScale.FillWidth,
                )
            }

        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LoginHeaderSection()

            Spacer(modifier = Modifier.height(48.dp))

            LoginButtonGroup()

            Spacer(modifier = Modifier.height(48.dp))

            LoginSection(
                modifier = Modifier.fillMaxWidth(),
                emailTextFieldState = emailTextFieldState,
                emailUiState = emailUiState,
                passwordTextFieldState = passwordTextFieldState,
                onLoginClicked = onLoginClicked,
                onForgotPasswordClicked = onForgotPasswordClicked,
            )
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
}

@Composable
fun LoginHeaderSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.login_header),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.login_sub_header),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    emailTextFieldState: TextFieldState,
    emailUiState: EmailUiState,
    passwordTextFieldState: TextFieldState,
    onLoginClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            textFieldState = emailTextFieldState,
            uiState = emailUiState,
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            textFieldState = passwordTextFieldState,
            modifier = Modifier.fillMaxWidth(),
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
            onClick = onLoginClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.button_login),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    uiState: EmailUiState,
) {
    OutlinedTextField(
        state = textFieldState,
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        onKeyboardAction = { performDefaultAction ->
            performDefaultAction()
        },
        placeholder = { Text(stringResource(R.string.hint_email)) },
        shape = RoundedCornerShape(8.dp),
        isError = uiState.hasError,
        supportingText = {
            if (uiState.hasError) {
                Text(
                    text = uiState.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    )
}

@Composable
fun PasswordTextField(modifier: Modifier = Modifier, textFieldState: TextFieldState) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedSecureTextField(
        state = textFieldState,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        textObfuscationMode =
            if (showPassword) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
        placeholder = { Text(stringResource(R.string.hint_password)) },
        trailingIcon = {
            Icon(
                if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = stringResource(R.string.cd_toggle_password_visibility),
                modifier = Modifier.clickable { showPassword = !showPassword }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginButtonGroup(modifier: Modifier = Modifier) {
    val options = listOf(
        stringResource(R.string.button_group_login),
        stringResource(R.string.button_group_register)
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1f))

        options.forEachIndexed { index, label ->
            ToggleButton(
                checked = selectedIndex == index,
                onCheckedChange = { selectedIndex = index },
                modifier = modifiers[index].semantics { role = Role.RadioButton },
                shapes =
                    when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    }
            ) {
                if (selectedIndex == index) Icon(
                    Icons.Filled.Check,
                    contentDescription = "Selected"
                )
                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                Text(label)
            }
        }
    }
}

@Preview
@Composable
fun LoginSectionPreview(modifier: Modifier = Modifier) {
    Autosweep20Theme {
        Surface {
            LoginScreen(
                emailTextFieldState = rememberTextFieldState(),
                emailUiState = EmailUiState(),
                passwordTextFieldState = rememberTextFieldState(),
                onLoginClicked = {},
                onForgotPasswordClicked = {},
                onQuickBalanceClicked = {},
            )
        }
    }
}
