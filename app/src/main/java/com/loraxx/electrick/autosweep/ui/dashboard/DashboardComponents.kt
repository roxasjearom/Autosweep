package com.loraxx.electrick.autosweep.ui.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loraxx.electrick.autosweep.R
import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme
import com.loraxx.electrick.autosweep.utils.toPhilippinePeso

@Composable
fun AccountBalanceSection(
    modifier: Modifier = Modifier,
    balanceDetails: BalanceDetails,
    onTopUpClick: () -> Unit,
    onTransactionClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextWithIcon(
                text = balanceDetails.plateNumber,
                iconId = R.drawable.ic_car,
                modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            )

            Text(
                text = balanceDetails.accountNumber,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = balanceDetails.accountBalance.toPhilippinePeso(),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(R.string.account_balance),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TopUpSection(onTopUpClick = onTopUpClick, onTransactionClick = onTransactionClick)
    }
}

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 16.dp,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onTertiary,
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
            style = MaterialTheme.typography.titleSmall,
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
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape,
                )
                .size(40.dp),
            onClick = onTransactionClick
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_transaction_history),
                contentDescription = stringResource(R.string.cd_transaction_history),
                tint = MaterialTheme.colorScheme.onSecondary,
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

@Preview
@Composable
fun AccountBalanceSectionPreview() {
    Autosweep20Theme {
        AccountBalanceSection(
            balanceDetails = BalanceDetails(
                plateNumber = "JCR 0623",
                accountNumber = "123456789",
                accountBalance = 1200.0
            ),
            onTopUpClick = {},
            onTransactionClick = {},
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
