package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory
import com.loraxx.electrick.autosweep.navigation.AccountTab
import com.loraxx.electrick.autosweep.navigation.CalculatorTab
import com.loraxx.electrick.autosweep.navigation.HomeTab
import com.loraxx.electrick.autosweep.navigation.TopLevelBackStack
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DashboardContainerScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel,
    onTopUpClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    val topLevelRoutes = listOf(HomeTab, CalculatorTab, AccountTab)
    val topLevelBackStack = remember { TopLevelBackStack<Any>(HomeTab) }
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
                            onTopUpClick = onTopUpClick,
                            onHistoryClick = onHistoryClick,
                            onActionBeltItemClick = onActionBeltItemClick,
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
                            label = { Text(stringResource(topLevelRoute.nameId)) },
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

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel,
    onTopUpClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(
        modifier = modifier,
        isRefreshing = uiState.isLoading,
        balanceDetails = uiState.balanceDetails,
        trafficAdvisory = uiState.trafficAdvisory,
        onTopUpClick = onTopUpClick,
        onHistoryClick = onHistoryClick,
        onRefresh = {
            viewModel.fetchBalanceDetails()
            viewModel.fetchTrafficAdvisory()
        },
        onActionBeltItemClick = onActionBeltItemClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    balanceDetails: BalanceDetails,
    trafficAdvisory: TrafficAdvisory,
    onTopUpClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onRefresh: () -> Unit,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        indicator = {
            PullToRefreshDefaults.LoadingIndicator(
                state = state,
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            AccountBalanceSection(
                balanceDetails = balanceDetails,
                onTopUpClick = onTopUpClick,
                onHistoryClick = onHistoryClick,
            )

            Spacer(modifier = Modifier.height(16.dp))

            ActionBeltSection(
                onActionBeltItemClick = onActionBeltItemClick,
            )

            AnimatedVisibility(
                visible = trafficAdvisory.hasAdvisory,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    TrafficAdvisorySection(advisoryMessage = trafficAdvisory.advisoryMessage)
                }
            }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    Autosweep20Theme {
        DashboardScreen(
            balanceDetails = BalanceDetails(
                plateNumber = "JCR 0623",
                accountNumber = "123456789",
                accountBalance = 1200.0
            ),
            trafficAdvisory = TrafficAdvisory(true, "Traffic advisory message"),
            isRefreshing = true,
            onTopUpClick = {},
            onHistoryClick = {},
            onRefresh = {},
            onActionBeltItemClick = {},
        )
    }
}
