package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.loraxx.electrick.autosweep.R

enum class ActionBeltItem(
    @StringRes val labelId: Int,
    @DrawableRes val drawableId: Int,
    val showBadge: Boolean = false,
) {
    RFID(R.string.action_belt_rfid, R.drawable.ic_belt_rfid),
    TRAFFIC(R.string.action_belt_traffic, R.drawable.ic_belt_traffic, true),
    TOLL_RATE(R.string.action_belt_toll_rate, R.drawable.ic_belt_toll_rate),
    HELP(R.string.action_belt_help, R.drawable.ic_belt_help),
}
