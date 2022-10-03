package com.finance_tracker.finance_tracker.screens.add_trnsaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier
) {
    CoinTheme {
        Column(
            modifier = modifier
        ) {
            CategoriesAppBar()

            AmountTextField(
                modifier = Modifier
                    .weight(1f),
                currency = "$",
                amount = 0.0
            )

            CalendarDayView(
                modifier = Modifier
            )

            Surface(
                elevation = 8.dp
            ) {
                Column {
                    StagesNavigationBar()

                    AddButtonSection(
                        paddingValues = WindowInsets.navigationBars.asPaddingValues()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddTransactionScreenPreview() {
    AddTransactionScreen()
}