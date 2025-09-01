package com.loraxx.electrick.autosweep.ui.quickbalance

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.accountNumberStateValidator
import com.loraxx.electrick.autosweep.ui.login.AccountNumberTextField
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.utils.toPhilippinePeso

@Composable
fun QuickBalanceScreen(
    modifier: Modifier = Modifier,
    viewModel: QuickBalanceViewModel,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuickBalanceScreen(
        modifier = modifier,
        accountBalance = uiState.accountBalance,
        accountNumberInputFieldState = uiState.accountNumberField,
        onCheckBalanceClick = { accountNumber -> viewModel.checkBalance(accountNumber) },
        navigateBack = navigateBack,
    )

    var showDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiState.showInvalidAccountError) {
        if (uiState.showInvalidAccountError) {
            showDialog = true
        }
        viewModel.onShowInvalidAccountErrorConsumed()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(R.string.registration_account_not_found)) },
            text = {
                Text(text = stringResource(R.string.registration_account_not_found_description))
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.button_label_okay))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuickBalanceScreen(
    modifier: Modifier = Modifier,
    accountBalance: Double,
    accountNumberInputFieldState: InputFieldState,
    onCheckBalanceClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            LargeFlexibleTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.quick_balance_header),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.surfaceTint,
                    )
                },
                subtitle = {
                    Text(
                        text = stringResource(R.string.quick_balance_sub_header),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val isInputValid = accountNumberInputFieldState.isValid()

            Spacer(modifier = Modifier.height(48.dp))

            AccountBalanceSection(accountBalance = accountBalance)

            Spacer(modifier = Modifier.height(24.dp))

            AccountNumberTextField(
                modifier = Modifier.fillMaxWidth(),
                accountNumberInputFieldState = accountNumberInputFieldState,
                imeAction = ImeAction.Done,
                onKeyboardActionClick = {
                    onCheckBalanceClick(
                        accountNumberInputFieldState.textFieldState.text.toString(),
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = isInputValid,
                onClick = {
                    onCheckBalanceClick(
                        accountNumberInputFieldState.textFieldState.text.toString(),
                    )
                },
            ) {
                Text(
                    stringResource(R.string.button_check_balance),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
fun AccountBalanceSection(modifier: Modifier = Modifier, accountBalance: Double) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.account_balance),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))

        val bounceAnimationSpec: FiniteAnimationSpec<IntOffset> =
            spring(dampingRatio = DampingRatioHighBouncy, stiffness = StiffnessHigh)

        AnimatedContent(
            targetState = accountBalance,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically(animationSpec = bounceAnimationSpec) { width -> -width } + fadeIn() togetherWith
                            slideOutVertically { width -> width } + fadeOut()
                } else {
                    slideInVertically(animationSpec = bounceAnimationSpec) { width -> width } + fadeIn() togetherWith
                            slideOutVertically { width -> -width } + fadeOut()
                }
            },
        ) { targetCount ->
            Text(
                text = targetCount.toPhilippinePeso(),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}

@Preview
@Composable
fun QuickBalancePreview() {
    Autosweep20Theme {
        QuickBalanceScreen(
            accountBalance = 1200.0,
            accountNumberInputFieldState = InputFieldState(validator = accountNumberStateValidator),
            onCheckBalanceClick = {},
            navigateBack = {},
        )
    }
}
