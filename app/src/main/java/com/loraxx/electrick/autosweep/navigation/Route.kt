package com.loraxx.electrick.autosweep.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)

@Serializable
data object Login : NavKey

@Serializable
data object QuickBalance : NavKey

@Serializable
data object Dashboard : NavKey

//Dashboard tabs
@Serializable
data object HomeTab : NavKey

@Serializable
data object CalculatorTab : NavKey

@Serializable
data object AccountTab : NavKey
