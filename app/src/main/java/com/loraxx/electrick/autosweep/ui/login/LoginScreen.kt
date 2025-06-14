package com.loraxx.electrick.autosweep.ui.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.StiffnessMedium
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    emailInputFieldState: InputFieldState,
    passwordInputFieldState: InputFieldState,
    selectedIndex: Int,
    onLoginClicked: (email: String, password: String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onQuickBalanceClicked: () -> Unit,
    onSelectedIndexChange: (Int) -> Unit,
    onRegisterClicked: (accountNumber: String, plateNumber: String) -> Unit,
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
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            val header = if (selectedIndex == 0) {
                stringResource(R.string.login_header)
            } else {
                stringResource(R.string.register_header)
            }

            val subHeader = if (selectedIndex == 0) {
                stringResource(R.string.login_sub_header)
            } else {
                stringResource(R.string.register_sub_header)
            }
            LoginHeaderSection(
                header = header,
                subHeader = subHeader,
            )

            Spacer(modifier = Modifier.height(48.dp))

            LoginButtonGroup(
                options = listOf(
                    stringResource(R.string.button_group_login),
                    stringResource(R.string.button_group_register)
                ),
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { index ->
                    onSelectedIndexChange(index)
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            val bounceAnimationSpec: FiniteAnimationSpec<IntOffset> =
                spring(dampingRatio = DampingRatioMediumBouncy, stiffness = StiffnessMedium)
            AnimatedContent(
                targetState = selectedIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(animationSpec = bounceAnimationSpec) { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally(animationSpec = bounceAnimationSpec) { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally(animationSpec = bounceAnimationSpec) { width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally(animationSpec = bounceAnimationSpec) { width -> width } + fadeOut()
                    }
                },
            ) { targetIndex ->
                if (targetIndex == 0) {
                    LoginSection(
                        modifier = Modifier.fillMaxWidth(),
                        emailInputFieldState = emailInputFieldState,
                        passwordInputFieldState = passwordInputFieldState,
                        onLoginClicked = onLoginClicked,
                        onForgotPasswordClicked = onForgotPasswordClicked,
                        onQuickBalanceClicked = onQuickBalanceClicked,
                    )
                } else {
                    RegistrationSection(
                        modifier = Modifier.fillMaxWidth(),
                        accountNumberInputFieldState = InputFieldState(),
                        plateNumberInputFieldState = InputFieldState(),
                        onRegisterClicked = onRegisterClicked,
                    )
                }
            }
        }
    }
}

@Composable
fun LoginHeaderSection(modifier: Modifier = Modifier, header: String, subHeader: String) {
    Column(modifier = modifier) {
        Text(
            text = header,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subHeader,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginButtonGroup(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1f))

        options.forEachIndexed { index, label ->
            ToggleButton(
                checked = selectedIndex == index,
                onCheckedChange = { onSelectedIndexChange(index) },
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
                emailInputFieldState = InputFieldState(),
                passwordInputFieldState = InputFieldState(),
                selectedIndex = 0,
                onLoginClicked = { _, _ -> },
                onForgotPasswordClicked = {},
                onQuickBalanceClicked = {},
                onSelectedIndexChange = {},
                onRegisterClicked = { _, _ -> },
            )
        }
    }
}
