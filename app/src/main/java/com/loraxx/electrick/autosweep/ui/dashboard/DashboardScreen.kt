package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loraxx.electrick.autosweep.domain.model.AccountDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.ui.topup.TopUpModalBottomSheet
import com.loraxx.electrick.autosweep.ui.topup.TopUpOption
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel,
    onTopUpOptionClick: (TopUpOption) -> Unit,
    onTransactionClick: (accountDetails: AccountDetails) -> Unit,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(
        modifier = modifier,
        isRefreshing = uiState.isLoading,
        accountDetailsList = uiState.accountDetailsList,
        trafficAdvisory = uiState.trafficAdvisory,
        newsItems = uiState.newsItems,
        onTopUpOptionClick = onTopUpOptionClick,
        onTransactionClick = onTransactionClick,
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
    accountDetailsList: List<AccountDetails>,
    trafficAdvisory: TrafficAdvisory,
    newsItems: List<NewsItem>,
    onTopUpOptionClick: (TopUpOption) -> Unit,
    onTransactionClick: (accountDetails: AccountDetails) -> Unit,
    onRefresh: () -> Unit,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    val state = rememberPullToRefreshState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showTopUpBottomSheet by remember { mutableStateOf(false) }

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
            if (isRefreshing) {
                ExpandableAccountBalanceCard(
                    accountDetails = AccountDetails(0, "-"),
                    isExpanded = true,
                    onTopUpClick = {},
                    onTransactionClick = {},
                    onCardClick = {},
                )
            } else {
                AccountBalanceList(
                    accountDetailsList = accountDetailsList,
                    onTopUpClick = {
                        showTopUpBottomSheet = true
                    },
                    onTransactionClick = onTransactionClick,
                )
            }

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

            AnimatedVisibility(
                visible = newsItems.isNotEmpty(),
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    NewsAndUpdateSection(newsItems = newsItems)
                }
            }

            if (showTopUpBottomSheet) {
                TopUpModalBottomSheet(
                    topUpOptions = TopUpOption.entries,
                    sheetState = sheetState,
                    onDismissRequest = { showTopUpBottomSheet = false },
                    onTopUpOptionClick = { topUpOption ->
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showTopUpBottomSheet = false
                            }
                        }
                        onTopUpOptionClick(topUpOption)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    Autosweep20Theme {
        DashboardScreen(
            accountDetailsList = listOf(
                AccountDetails(
                    plateNumber = "JCR 0623",
                    accountNumber = 1234567,
                    accountBalance = 1200.0,
                )
            ),
            trafficAdvisory = TrafficAdvisory(true, "Traffic advisory message"),
            newsItems = listOf(
                NewsItem(
                    newsId = 1,
                    title = "New DOTr Secretary suspends ‘No RFID, No entry’ on expressways",
                    imageUrl = "https://picsum.photos/200/300",
                ),
                NewsItem(
                    newsId = 2,
                    title = "Why isn’t my RFID sticker being read properly",
                    imageUrl = "https://picsum.photos/200/300",
                ),
            ),
            isRefreshing = true,
            onTopUpOptionClick = {},
            onTransactionClick = {},
            onRefresh = {},
            onActionBeltItemClick = {},
        )
    }
}
