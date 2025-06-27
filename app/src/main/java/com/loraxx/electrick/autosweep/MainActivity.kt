package com.loraxx.electrick.autosweep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.loraxx.electrick.autosweep.navigation.Login
import com.loraxx.electrick.autosweep.navigation.QuickBalance
import com.loraxx.electrick.autosweep.ui.login.LoginScreen
import com.loraxx.electrick.autosweep.ui.login.LoginViewModel
import com.loraxx.electrick.autosweep.ui.quickbalance.QuickBalanceScreen
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Autosweep20Theme {
                val loginViewModel: LoginViewModel by viewModels()

                val backStack = rememberNavBackStack(Login)

                Scaffold { paddingValues ->
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            entry<Login> {
                                LoginScreen(
                                    loginViewModel = loginViewModel,
                                    onForgotPasswordClicked = {
                                        //TODO implement forgot password here
                                    },
                                    onQuickBalanceClicked = {
                                        backStack.add(QuickBalance)
                                    }
                                )
                            }
                            entry<QuickBalance> {
                                QuickBalanceScreen()
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
