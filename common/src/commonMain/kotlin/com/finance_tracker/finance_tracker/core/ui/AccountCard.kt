package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.detail_account.DetailAccountData
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountCard(
    data: Account,
    modifier: Modifier = Modifier
) {
    val navController = LocalRootController.current.findRootController()
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(
                width = 160.dp,
                height = 128.dp
            ),
        backgroundColor = data.color,
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navController.push(
                screen = MainNavigationTree.DetailAccount.name,
                params = DetailAccountData(
                    color = data.color,
                    name = data.name
                )
            )
        }
    ) {
        Column {
            Icon(
                painter = rememberVectorPainter("ic_wallet_active"),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(29.dp),
                tint = CoinTheme.color.primaryVariant
            )
            Text(
                text = data.currency.sign + DecimalFormatType.Amount.format(data.balance),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.primaryVariant,
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        start = 16.dp
                    )
            )
            val accountName = if (data.type == Account.Type.Cash) {
                "${data.name} (${data.currency.sign})"
            } else {
                data.name
            }
            Text(
                text = accountName,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.primaryVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
    }
}