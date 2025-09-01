package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.domain.model.AccountDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.utils.toPhilippinePeso

@Composable
fun AccountBalanceList(
    modifier: Modifier = Modifier,
    accountDetailsList: List<AccountDetails>,
    onTopUpClick: (AccountDetails) -> Unit,
    onTransactionClick: (AccountDetails) -> Unit,
) {
    val firstAccount = accountDetailsList.first()
    var expandedAccountNumber by remember { mutableStateOf<Int?>(firstAccount.accountNumber) }
    Column(modifier = modifier.fillMaxWidth()) {
        accountDetailsList.forEachIndexed { index, accountDetails ->
            val isExpanded = accountDetails.accountNumber == expandedAccountNumber
            ExpandableAccountBalanceCard(
                accountDetails = accountDetails,
                isExpanded = isExpanded,
                onCardClick = {
                    expandedAccountNumber = if (isExpanded) null else accountDetails.accountNumber
                },
                onTopUpClick = { onTopUpClick(accountDetails) },
                onTransactionClick = { onTransactionClick(accountDetails) }
            )
            if (index < accountDetailsList.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ExpandableAccountBalanceCard(
    modifier: Modifier = Modifier,
    accountDetails: AccountDetails,
    isExpanded: Boolean = false,
    onTopUpClick: (accountDetails: AccountDetails) -> Unit,
    onTransactionClick: (accountDetails: AccountDetails) -> Unit,
    onCardClick: (accountDetails: AccountDetails) -> Unit,
) {
    SharedTransitionLayout {
        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                (expandVertically(expandFrom = Alignment.Top))
                    .togetherWith(shrinkVertically(shrinkTowards = Alignment.Top))
            },
            label = "AccountBalanceAnimation"
        ) { targetExpanded ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onCardClick(accountDetails)
                    }
            ) {
                if (targetExpanded) {
                    AccountBalanceExpanded(
                        accountDetails = accountDetails,
                        onTopUpClick = onTopUpClick,
                        onTransactionClick = onTransactionClick,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                } else {
                    AccountBalanceMinimized(
                        accountDetails = accountDetails,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AccountBalanceExpanded(
    modifier: Modifier = Modifier,
    accountDetails: AccountDetails,
    onTopUpClick: (accountDetails: AccountDetails) -> Unit,
    onTransactionClick: (accountDetails: AccountDetails) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        with(sharedTransitionScope) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithIcon(
                    text = accountDetails.plateNumber,
                    iconId = R.drawable.ic_car,
                    modifier = Modifier.clip(RoundedCornerShape(4.dp)),
                )

                Text(
                    text = accountDetails.accountNumber.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = accountDetails.accountBalance.toPhilippinePeso(),
                modifier = Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(key = "accountBalanceText"),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = stringResource(R.string.account_balance),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TopUpSection(onTopUpClick = {
                onTopUpClick(accountDetails)
            }, onTransactionClick = {
                onTransactionClick(accountDetails)
            })
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AccountBalanceMinimized(
    modifier: Modifier = Modifier,
    accountDetails: AccountDetails,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(sharedTransitionScope) {
            TextWithIcon(
                text = accountDetails.plateNumber,
                iconId = R.drawable.ic_car,
                modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            )

            Text(
                text = accountDetails.accountBalance.toPhilippinePeso(),
                modifier = Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(key = "accountBalanceText"),
                    animatedVisibilityScope = animatedVisibilityScope,
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun TextWithIcon(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    iconSize: Dp = 16.dp,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryFixed,
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryFixed,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
) {
    Row(
        modifier = modifier
            .background(containerColor)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = text,
            modifier = Modifier.size(iconSize),
            tint = contentColor,
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = text,
            style = textStyle,
            color = contentColor,
        )
    }
}

@Composable
fun TopUpSection(
    modifier: Modifier = Modifier,
    onTopUpClick: () -> Unit,
    onTransactionClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = onTopUpClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                stringResource(R.string.button_top_up),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryFixed,
                    shape = CircleShape,
                )
                .size(40.dp),
            onClick = onTransactionClick
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_transaction_history),
                contentDescription = stringResource(R.string.cd_transaction_history),
                tint = MaterialTheme.colorScheme.onSecondaryFixed,
            )
        }
    }
}

@Composable
fun ActionBeltSection(
    modifier: Modifier = Modifier,
    onActionBeltItemClick: (ActionBeltItem) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        for (actionBeltItem in ActionBeltItem.entries) {
            ActionBeltButton(
                text = stringResource(actionBeltItem.labelId),
                drawableId = actionBeltItem.drawableId,
                showBadge = actionBeltItem.showBadge,
                onClick = { onActionBeltItemClick(actionBeltItem) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ActionBeltButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes drawableId: Int,
    showBadge: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FilledTonalIconButton(
            modifier = Modifier.size(
                IconButtonDefaults.mediumContainerSize(
                    IconButtonDefaults.IconButtonWidthOption.Wide
                )
            ),
            colors = IconButtonDefaults.filledTonalIconButtonColors()
                .copy(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            shape = IconButtonDefaults.smallSquareShape,
            onClick = onClick,
        ) {
            BadgedBox(
                badge = { if (showBadge) Badge() }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(drawableId),
                    contentDescription = text,
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun TrafficAdvisorySection(modifier: Modifier = Modifier, advisoryMessage: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_traffic_advisory),
            contentDescription = "Traffic advisory",
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = advisoryMessage,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAndUpdateSection(modifier: Modifier = Modifier, newsItems: List<NewsItem>) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "News and update",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { newsItems.count() },
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
        ) { i ->
            val item = newsItems[i]
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(186.dp, 128.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge),
                )
                Text(
                    modifier = modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun AccountBalanceSectionPreview() {
    Autosweep20Theme {
        ExpandableAccountBalanceCard(
            accountDetails = AccountDetails(
                plateNumber = "JCR 0623",
                accountNumber = 1234567,
                accountBalance = 1200.0
            ),
            onTopUpClick = {},
            onTransactionClick = {},
            onCardClick = {},
        )
    }
}

@Preview
@Composable
fun ActionBeltIconPreview() {
    Autosweep20Theme {
        ActionBeltSection(
            onActionBeltItemClick = {},
        )
    }
}

@Preview
@Composable
fun TrafficAdvisoryPreview() {
    Autosweep20Theme {
        TrafficAdvisorySection(advisoryMessage = "TRAFFIC ADVISORY: Expect delays on TPLEX due to an ongoing incident. Drive safe!")
    }
}

@Preview
@Composable
fun NewsAndUpdateSectionPreview() {
    Autosweep20Theme {
        NewsAndUpdateSection(
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
            )
        )
    }
}
