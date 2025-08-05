package com.loraxx.electrick.autosweep.ui.topup

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface TopUpItem {
    val id: String

    @get:StringRes
    val topUpName: Int

    @get:DrawableRes
    val topUpLogo: Int
}
