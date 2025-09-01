package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.domain.model.AccountDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory
import com.loraxx.electrick.autosweep.domain.repository.DashboardRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(): DashboardRepository {
    //TODO Update these functions once we have a valid API
    override suspend fun getAccountDetails(): AccountDetails {
        delay(3000)
        return AccountDetails(
            plateNumber = "JCR 0623",
            accountNumber = 1234567,
            accountBalance = 1200.0,
        )
    }

    override suspend fun geAccountDetailsList(): List<AccountDetails> {
        delay(3000)
        return listOf(
            AccountDetails(
                accountNumber = 1234567,
                plateNumber = "JCR 0623",
                accountBalance = 1200.0,
            ),
            AccountDetails(
                accountNumber = 1122334,
                plateNumber = "MCC 0417",
                accountBalance = 2600.0,
            ),
            AccountDetails(
                accountNumber = 2002223,
                plateNumber = "ABC 1234",
                accountBalance = 850.0,
            ),
        )
    }

    override suspend fun getTrafficAdvisory(): TrafficAdvisory {
        return TrafficAdvisory(
            hasAdvisory = true,
            advisoryMessage = "TRAFFIC ADVISORY: Expect delays on TPLEX due to an ongoing incident. Drive safe!",
        )
    }

    override suspend fun getNews(): List<NewsItem> {
        return listOf(
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
            NewsItem(
                newsId = 3,
                title = "Incoming toll increase by the end of year",
                imageUrl = "https://picsum.photos/200/300",
            ),
        )
    }
}
