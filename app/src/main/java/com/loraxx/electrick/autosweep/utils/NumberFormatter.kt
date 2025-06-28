package com.loraxx.electrick.autosweep.utils

import java.text.DecimalFormat

fun Double.toPhilippinePeso(): String {
    return DecimalFormat("₱#,##0.00").format(this)
}
