package com.loraxx.electrick.autosweep.ui.topup.bank

import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.ui.topup.TopUpItem

enum class BankTopUp() : TopUpItem {
    BPI {
        override val id = "bank_bpi"
        override val topUpName = R.string.bank_bpi
        override val topUpLogo = R.drawable.ic_bank_bpi
    },
    RCBC {
        override val id = "bank_rcbc"
        override val topUpName = R.string.bank_rcbc
        override val topUpLogo = R.drawable.ic_bank_rcbc
    },
    UB {
        override val id = "bank_ub"
        override val topUpName = R.string.bank_ub
        override val topUpLogo = R.drawable.ic_bank_ub
    },
}
