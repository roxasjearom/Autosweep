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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel,
    onTopUpClick: () -> Unit,
    onTransactionClick: () -> Unit,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(
        modifier = modifier,
        isRefreshing = uiState.isLoading,
        balanceDetails = uiState.balanceDetails,
        trafficAdvisory = uiState.trafficAdvisory,
        newsItems = uiState.newsItems,
        onTopUpClick = onTopUpClick,
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
    balanceDetails: BalanceDetails,
    trafficAdvisory: TrafficAdvisory,
    newsItems: List<NewsItem>,
    onTopUpClick: () -> Unit,
    onTransactionClick: () -> Unit,
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
                onTransactionClick = onTransactionClick,
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
            onTopUpClick = {},
            onTransactionClick = {},
            onRefresh = {},
            onActionBeltItemClick = {},
        )
    }
}
