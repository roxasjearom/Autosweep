package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.utils.toPhilippinePeso

@Composable
fun AccountBalanceSection(
    modifier: Modifier = Modifier,
    balanceDetails: BalanceDetails,
    onTopUpClick: () -> Unit,
    onHistoryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextWithIcon(
                text = balanceDetails.plateNumber,
                iconId = R.drawable.ic_car,
                modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            )

            Text(
                text = balanceDetails.accountNumber,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = balanceDetails.accountBalance.toPhilippinePeso(),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(R.string.account_balance),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TopUpSection(onTopUpClick = onTopUpClick, onHistoryClick = onHistoryClick)
    }
}

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 16.dp,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onTertiary,
) {
    Row(
        modifier = modifier
            .background(containerColor)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = text,
            modifier = Modifier.size(iconSize),
            tint = contentColor,
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor,
        )
    }
}

@Composable
fun TopUpSection(
    modifier: Modifier = Modifier,
    onTopUpClick: () -> Unit,
    onHistoryClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = onTopUpClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                stringResource(R.string.button_top_up),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            ),
            onClick = onHistoryClick
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_transaction_history),
                contentDescription = stringResource(R.string.cd_transaction_history),
                tint = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Preview
@Composable
fun AccountBalanceSectionPreview(modifier: Modifier = Modifier) {
    Autosweep20Theme {
        AccountBalanceSection(
            balanceDetails = BalanceDetails(
                plateNumber = "JCR 0623",
                accountNumber = "123456789",
                accountBalance = 1200.0
            ),
            onTopUpClick = {},
            onHistoryClick = {},
        )
    }
}
