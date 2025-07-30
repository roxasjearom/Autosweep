package com.loraxx.electrick.autosweep.ui.topup.bank

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.loraxx.electrick.autosweep.R

enum class BankTopUp(
    @param:StringRes val bankName: Int,
    @param:DrawableRes val bankLogo: Int,
) {
    BPI(R.string.bank_bpi, R.drawable.ic_bank_bpi),
    RCBC(R.string.bank_rcbc, R.drawable.ic_bank_rcbc),
    UB(R.string.bank_ub, R.drawable.ic_bank_ub),
}
