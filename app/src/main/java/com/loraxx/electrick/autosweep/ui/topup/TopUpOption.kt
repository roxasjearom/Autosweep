package com.loraxx.electrick.autosweep.ui.topup

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.ui.graphics.vector.ImageVector
import com.loraxx.electrick.autosweep.R

enum class TopUpOption(
    @param:StringRes val topUpName: Int,
    @param:StringRes val topUpDescription: Int,
    val imageVector: ImageVector,
) {
    BANK_ACCOUNT(
        topUpName = R.string.bank_account_name,
        topUpDescription = R.string.bank_account_description,
        imageVector = Icons.Outlined.AccountBalance,
    ),
    E_WALLET(
        topUpName = R.string.e_wallet_name,
        topUpDescription = R.string.e_wallet_description,
        imageVector = Icons.Outlined.AccountBalanceWallet,
    ),
    CREDIT_CARD(
        topUpName = R.string.credit_card_name,
        topUpDescription = R.string.credit_card_description,
        imageVector = Icons.Outlined.CreditCard,
    ),
}
