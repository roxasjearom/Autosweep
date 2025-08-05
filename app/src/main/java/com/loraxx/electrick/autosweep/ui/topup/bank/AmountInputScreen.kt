package com.loraxx.electrick.autosweep.ui.topup.bank

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.fields.InputFieldState
import com.loraxx.electrick.autosweep.ui.fields.topUpAmountValidator
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.ui.topup.AmountInputTextField
import com.loraxx.electrick.autosweep.ui.topup.TopUpItem

@Composable
fun AmountInputScreen(
    modifier: Modifier = Modifier,
    selectedTopUpItem: TopUpItem,
    viewModel: AmountInputViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AmountInputScreen(
        modifier = modifier,
        selectedTopUpItem = selectedTopUpItem,
        amountInputState = uiState.topUpAmountField,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AmountInputScreen(
    modifier: Modifier = Modifier,
    selectedTopUpItem: TopUpItem,
    amountInputState: InputFieldState,
) {
    val bankName = stringResource(selectedTopUpItem.topUpName)
    var showConfirmBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = stringResource(R.string.top_up_from, bankName),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(40.dp))

            AmountInputTextField(
                modifier = Modifier.fillMaxWidth(),
                amountInputFieldState = amountInputState,
                imeAction = ImeAction.Done,
            )

            if (showConfirmBottomSheet) {
                //TODO show confirm dialog
            }
        }

        val size = ButtonDefaults.MediumContainerHeight
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentPadding = ButtonDefaults.contentPaddingFor(size),
            shape = ButtonDefaults.squareShape,
            enabled = amountInputState.isValid(),
            onClick = {
                showConfirmBottomSheet = true
            },
        ) {
            Text(
                stringResource(R.string.button_label_continue),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }


}

@Preview
@Composable
fun AmountInputScreenPreview() {
    Autosweep20Theme {
        Surface {
            AmountInputScreen(
                selectedTopUpItem = BankTopUp.BPI,
                amountInputState = InputFieldState(validator = topUpAmountValidator),
            )
        }
    }
}
