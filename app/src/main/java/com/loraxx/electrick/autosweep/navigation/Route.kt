package com.loraxx.electrick.autosweep.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.loraxx.electrick.autosweep.R
import kotlinx.serialization.Serializable

sealed interface TopLevelRoute {
    val nameId: Int
    val unselectedIcon: ImageVector
    val selectedIcon: ImageVector

    fun getIcon(isSelected: Boolean): ImageVector = if (isSelected) selectedIcon else unselectedIcon
}

//Dashboard tabs
@Serializable
data object HomeTab : NavKey, TopLevelRoute {
    override val nameId = R.string.dashboard_tab_home
    override val unselectedIcon = Icons.Outlined.Home
    override val selectedIcon = Icons.Filled.Home
}

@Serializable
data object CalculatorTab : NavKey, TopLevelRoute {
    override val nameId = R.string.dashboard_tab_calculator
    override val unselectedIcon = Icons.Outlined.Calculate
    override val selectedIcon = Icons.Filled.Calculate
}

@Serializable
data object AccountTab : NavKey, TopLevelRoute {
    override val nameId = R.string.dashboard_tab_account
    override val unselectedIcon = Icons.Outlined.Person
    override val selectedIcon = Icons.Filled.Person
}
@Serializable
data object Login : NavKey

@Serializable
data object QuickBalance : NavKey

@Serializable
data object Dashboard : NavKey

//Dashboard subscreens
@Serializable
data object TopUp : NavKey

@Serializable
data object Transaction : NavKey

//Dashboard Action Belt screens
@Serializable
data object Rfid : NavKey

@Serializable
data object Traffic : NavKey

@Serializable
data object TollRate : NavKey

@Serializable
data object Help : NavKey
