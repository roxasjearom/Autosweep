package com.loraxx.electrick.autosweep.ui.topup.bank

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.ui.topup.TopUpItem

@Composable
fun SelectBankScreen(
    modifier: Modifier = Modifier,
    topUpItems: List<TopUpItem>,
    onBankClick: (TopUpItem) -> Unit,
) {
    Column(modifier = modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxSize()) {
        Text(
            text = stringResource(R.string.top_up_link_bank_account),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.top_up_link_bank_description),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(24.dp))

        topUpItems.forEach { topUpItem ->
            TopUpItem(
                itemName = stringResource(topUpItem.topUpName),
                icon = {
                    Image(
                        painter = painterResource(id = topUpItem.topUpLogo),
                        contentDescription = stringResource(topUpItem.topUpName),
                        modifier = Modifier.size(40.dp),
                    )
                },
                onClick = {
                    onBankClick(topUpItem)
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
fun BankTopUpScreenPreview() {
    Autosweep20Theme {
        Surface {
            SelectBankScreen(
                topUpItems = listOf(BankTopUp.BPI, BankTopUp.RCBC, BankTopUp.UB),
                onBankClick = {},
            )
        }
    }
}
