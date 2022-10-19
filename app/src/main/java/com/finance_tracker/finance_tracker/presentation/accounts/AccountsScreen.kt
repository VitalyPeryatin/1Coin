package com.finance_tracker.finance_tracker.presentation.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
import com.finance_tracker.finance_tracker.core.ui.AccountCard
import com.finance_tracker.finance_tracker.domain.models.Account
import com.ramcosta.composedestinations.annotation.Destination

@TabNavGraph
@Destination
@Composable
fun AccountsScreen() {

    //TODO - ВРЕМЕННЫЕ ФЕЙКОВЫЕ ДАННЫЕ
    val cards = listOf(
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFD50000),
        ),
        Account(
            id = 1,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFE67C73),
        ),
        Account(
            id = 2,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF4511E),
        ),
        Account(
            id = 3,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF6BF26),
        ),
        Account(
            id = 4,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF33B679),
        ),  Account(
            id = 5,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF0B8043),
        ),
        Account(
            id = 6,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF039BE5),
        ),
        Account(
            id = 7,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF3F51B5),
        ),
        Account(
            id = 8,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFD50000),
        ),
        Account(
            id = 9,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFE67C73),
        ),
        Account(
            id = 10,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF4511E),
        ),
        Account(
            id = 11,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF6BF26),
        ),
        Account(
            id = 12,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF33B679),
        ),
        Account(
            id = 13,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF0B8043),
        ),
        Account(
            id = 14,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF039BE5),
        ),
        Account(
            id = 15,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF3F51B5),
        ),
    )


    Column {

        AccountsAppBar()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxHeight(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            items(cards) { card ->
                AccountCard(data = card)
            }
        }
    }

}

@Preview
@Composable
fun AccountsScreenPreview() {
    AccountsScreen()
}
