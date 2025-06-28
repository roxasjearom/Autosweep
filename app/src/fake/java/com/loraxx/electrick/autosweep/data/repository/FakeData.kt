package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.domain.model.TollAccountDetails

object FakeData {
    const val ADMIN_PASSWORD = "admin"

    val fakeRegistrationData = listOf(
        Pair("1234567", "ABC1234"),
        Pair("112233", "XYZ1122"),
        Pair("7777777", "DEF7777"),
        Pair("7654321", "GHI7654"),
        Pair("3334444", "JKL3344")
    )
    val fakeTollAccountDetails = listOf(
        TollAccountDetails("admin@admin.com", "1234567", "ABC1234", 1560.0),
        TollAccountDetails("admin123@admin.com", "112233", "XYZ1122", 10160.0),
        TollAccountDetails("admin777@admin.com", "7777777", "DEF7777", 7700.0),
        TollAccountDetails("admin765@admin.com", "7654321", "GHI7654", 705.0),
        TollAccountDetails("admin34@admin.com", "3334444", "JKL3344", 34050.0),
    )
}
