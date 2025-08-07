package com.loraxx.electrick.autosweep.ui.topup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.then
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.DigitOnlyInputTransformation
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.ValidationState
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.utils.toPhilippinePeso

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpModalBottomSheet(
    modifier: Modifier = Modifier,
    topUpOptions: List<TopUpOption>,
    sheetState: SheetState,
    onTopUpOptionClick: (TopUpOption) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = stringResource(R.string.top_up_bottom_sheet_header),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(32.dp))

            topUpOptions.forEach { topUpOption ->
                TopUpOption(
                    imageVector = topUpOption.imageVector,
                    topUpName = stringResource(topUpOption.topUpName),
                    topUpDescription = stringResource(topUpOption.topUpDescription),
                    onClick = {
                        onTopUpOptionClick(topUpOption)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmTransactionBottomSheet(
    modifier: Modifier = Modifier,
    amount: Double,
    selectedTopUp: String,
    transactionFee: Double,
    sheetState: SheetState,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        ConfirmTransaction(
            modifier = modifier,
            amount = amount,
            selectedTopUp = selectedTopUp,
            transactionFee = transactionFee,
            onConfirmClick = onConfirmClick,
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ConfirmTransaction(
    modifier: Modifier = Modifier,
    amount: Double,
    selectedTopUp: String,
    transactionFee: Double,
    onConfirmClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.top_up_confirm_transaction),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = amount.toPhilippinePeso(),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledItem(
            label = stringResource(R.string.label_from),
            value = selectedTopUp,
        )

        HorizontalDivider()

        LabeledItem(
            label = stringResource(R.string.label_transaction_fee),
            value = transactionFee.toPhilippinePeso(),
        )

        HorizontalDivider()

        LabeledItem(
            label = stringResource(R.string.label_total),
            value = (amount + transactionFee).toPhilippinePeso(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            contentPadding = ButtonDefaults.contentPaddingFor(ButtonDefaults.MediumContainerHeight),
            shape = ButtonDefaults.squareShape,
            onClick = onConfirmClick,
        ) {
            Text(
                stringResource(R.string.button_confirm),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun LabeledItem(modifier: Modifier = Modifier, label: String, value: String) {
    Row(modifier = modifier.padding(vertical = 16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun TopUpOption(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    topUpName: String,
    topUpDescription: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleIcon {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = imageVector,
                contentDescription = topUpName,
                tint = MaterialTheme.colorScheme.secondary,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = topUpName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = topUpDescription,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )

        }
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = topUpName,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun TopUpItem(
    modifier: Modifier = Modifier,
    itemName: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleIcon {
            icon()
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = itemName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = itemName,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun CircleIcon(
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    circleSize: Dp = 40.dp,
    iconSize: Dp = 24.dp,
    icon: @Composable () -> Unit,
) {
    Surface(
        shape = CircleShape,
        color = backgroundColor,
        modifier = Modifier.size(circleSize)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(iconSize),
        ) {
            icon()
        }
    }
}

@Composable
fun AmountInputTextField(
    modifier: Modifier = Modifier,
    amountInputFieldState: InputFieldState,
    imeAction: ImeAction = ImeAction.Next,
    onKeyboardActionClick: () -> Unit = { },
) {
    val hasError = amountInputFieldState.getValidationState() == ValidationState.INVALID

    OutlinedTextField(
        state = amountInputFieldState.textFieldState,
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
            .maxLength(10)
            .then(DigitOnlyInputTransformation()),
        label = { Text(stringResource(R.string.top_up_enter_amount)) },
        placeholder = { Text(stringResource(R.string.top_up_empty_amount)) },
        shape = RoundedCornerShape(8.dp),
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = when (amountInputFieldState.getValidationState()) {
                        ValidationState.INVALID -> stringResource(R.string.top_up_amount_too_low)
                        ValidationState.VALID, ValidationState.INITIAL -> ""
                    },
                    color = MaterialTheme.colorScheme.error,
                )
            } else {
                Text(
                    text = stringResource(R.string.top_up_minimum_amount),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}

@Preview
@Composable
fun ConfirmTransactionPreview() {
    Autosweep20Theme {
        Surface {
            ConfirmTransaction(
                amount = 500.0,
                selectedTopUp = "BPI",
                transactionFee = 5.0,
                onConfirmClick = {},
            )
        }
    }
}

@Preview
@Composable
fun TopUpItemPreview() {
    Autosweep20Theme {
        Surface {
            TopUpItem(
                itemName = "BPI",
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bank_ub),
                        contentDescription = "BPI",
                        modifier = Modifier.size(40.dp),
                    )
                },
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
fun TopUpOptionPreview() {
    Autosweep20Theme {
        TopUpOption(
            imageVector = Icons.Filled.AccountBalance,
            topUpName = "Bank account",
            topUpDescription = "Link your bank account",
            onClick = {}
        )
    }
}
