package com.loraxx.electrick.autosweep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.loraxx.electrick.autosweep.navigation.Dashboard
import com.loraxx.electrick.autosweep.navigation.Login
import com.loraxx.electrick.autosweep.navigation.QuickBalance
import com.loraxx.electrick.autosweep.ui.dashboard.DashboardContainerScreen
import com.loraxx.electrick.autosweep.ui.dashboard.DashboardViewModel
import com.loraxx.electrick.autosweep.ui.login.LoginScreen
import com.loraxx.electrick.autosweep.ui.login.LoginViewModel
import com.loraxx.electrick.autosweep.ui.quickbalance.QuickBalanceScreen
import com.loraxx.electrick.autosweep.ui.quickbalance.QuickBalanceViewModel
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Autosweep20Theme {
                val backStack = rememberNavBackStack(Login)

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators = listOf(
                        rememberSceneSetupNavEntryDecorator(),
                        rememberSavedStateNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        val loginViewModel: LoginViewModel = hiltViewModel()
                        entry<Login> {
                            LoginScreen(
                                loginViewModel = loginViewModel,
                                onForgotPasswordClicked = {
                                    //TODO implement forgot password here
                                },
                                onQuickBalanceClicked = {
                                    backStack.add(QuickBalance)
                                },
                                navigateToDashboard = {
                                    backStack.removeLastOrNull()
                                    backStack.add(Dashboard)
                                }
                            )
                        }
                        entry<QuickBalance> {
                            val quickBalanceViewModel: QuickBalanceViewModel = hiltViewModel()
                            QuickBalanceScreen(
                                viewModel = quickBalanceViewModel,
                                navigateBack = { backStack.removeLastOrNull() }
                            )
                        }
                        entry<Dashboard> {
                            val dashboardViewModel: DashboardViewModel = hiltViewModel()
                            DashboardContainerScreen(viewModel = dashboardViewModel)
                        }
                    },
                    transitionSpec = {
                        slideInHorizontally(initialOffsetX = { it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { -it })
                    },
                    popTransitionSpec = {
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { it })
                    },
                    predictivePopTransitionSpec = {
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { it })
                    },
                )
            }
        }
    }
}
