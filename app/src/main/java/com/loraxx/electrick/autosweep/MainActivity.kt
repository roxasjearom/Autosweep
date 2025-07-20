package com.loraxx.electrick.autosweep

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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
import com.loraxx.electrick.autosweep.ui.dashboard.ActionBeltItem
import com.loraxx.electrick.autosweep.ui.dashboard.DashboardScreen
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

                Scaffold { paddingValues ->
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
                                DashboardScreen(
                                    viewModel = dashboardViewModel,
                                    onTopUpClick = {
                                        //TODO navigate to Top up screen
                                    },
                                    onHistoryClick = {
                                        //TODO navigate to Transaction History screen
                                    },
                                    onActionBeltItemClick = { actionBeltItem ->
                                        when (actionBeltItem) {
                                            ActionBeltItem.RFID -> {
                                                //TODO navigate to RFID screen
                                                Toast.makeText(this@MainActivity, "Navigate to RFID screen", Toast.LENGTH_SHORT).show()
                                            }

                                            ActionBeltItem.TRAFFIC -> {
                                                //TODO navigate to Traffic screen
                                                Toast.makeText(this@MainActivity, "Navigate to Traffic screen", Toast.LENGTH_SHORT).show()
                                            }

                                            ActionBeltItem.TOLL_RATE -> {
                                                //TODO navigate to Toll Rate screen
                                                Toast.makeText(this@MainActivity, "Navigate to Toll Rate screen", Toast.LENGTH_SHORT).show()
                                            }

                                            ActionBeltItem.HELP -> {
                                                //TODO navigate to Help screen
                                                Toast.makeText(this@MainActivity, "Navigate to Help screen", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    },
                                )
                            }
                        },
                        modifier = Modifier.padding(paddingValues),
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
}
