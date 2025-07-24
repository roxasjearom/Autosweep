package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.navigation.AccountTab
import com.loraxx.electrick.autosweep.navigation.CalculatorTab
import com.loraxx.electrick.autosweep.navigation.Help
import com.loraxx.electrick.autosweep.navigation.HomeTab
import com.loraxx.electrick.autosweep.navigation.Rfid
import com.loraxx.electrick.autosweep.navigation.TollRate
import com.loraxx.electrick.autosweep.navigation.TopLevelBackStack
import com.loraxx.electrick.autosweep.navigation.TopUp
import com.loraxx.electrick.autosweep.navigation.Traffic
import com.loraxx.electrick.autosweep.navigation.Transaction

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DashboardContainerScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel,
) {
    val topLevelRoutes = listOf(HomeTab, CalculatorTab, AccountTab)
    val topLevelBackStack = remember { TopLevelBackStack<NavKey>(HomeTab) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.dashboard_my_account),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    if (topLevelBackStack.showNavigationIcon()) {
                        IconButton(onClick = {
                            topLevelBackStack.removeLast()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigate back"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        //TODO navigate to Add account screen
                    }) {
                        Icon(
                            imageVector = Icons.Filled.PersonAdd,
                            contentDescription = "Add account",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavDisplay(
                backStack = topLevelBackStack.backStack,
                onBack = { topLevelBackStack.removeLast() },
                entryProvider = entryProvider {
                    entry<HomeTab> {
                        DashboardScreen(
                            viewModel = viewModel,
                            onTopUpClick = {
                                topLevelBackStack.add(TopUp)
                            },
                            onTransactionClick = {
                                topLevelBackStack.add(Transaction)
                            },
                            onActionBeltItemClick = { actionBeltItem ->
                                when (actionBeltItem) {
                                    ActionBeltItem.RFID -> topLevelBackStack.add(Rfid)
                                    ActionBeltItem.TRAFFIC -> topLevelBackStack.add(Traffic)
                                    ActionBeltItem.TOLL_RATE -> topLevelBackStack.add(TollRate)
                                    ActionBeltItem.HELP -> topLevelBackStack.add(Help)
                                }
                            },
                        )
                    }
                    entry<CalculatorTab> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Calculator screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<AccountTab> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Account screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<TopUp> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Top Up screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<Transaction> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Transaction History screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<Rfid> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "RFID screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<Traffic> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Traffic screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<TollRate> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Toll Rate screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                    entry<Help> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Help screen",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                },
            )

            HorizontalFloatingToolbar(
                expanded = true,
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .height(72.dp)
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(16.dp),
                content = {
                    topLevelRoutes.forEach { topLevelRoute ->
                        val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = topLevelRoute.getIcon(isSelected),
                                    contentDescription = stringResource(topLevelRoute.nameId)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(topLevelRoute.nameId),
                                    fontWeight = FontWeight.Medium,
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                topLevelBackStack.addTopLevel(topLevelRoute)
                            }
                        )
                    }
                },
            )
        }
    }
}
