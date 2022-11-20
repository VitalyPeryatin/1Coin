package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffold
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldScopeInstance.align
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.ScrollStrategy
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.detail_account.views.AccountNameText
import com.finance_tracker.finance_tracker.presentation.detail_account.views.DetailAccountAppBar
import com.finance_tracker.finance_tracker.presentation.detail_account.views.DetailAccountExpandedAppBar
import com.finance_tracker.finance_tracker.presentation.detail_account.views.EditButton
import org.koin.core.parameter.parametersOf
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
@Suppress("MagicNumber")
fun DetailAccountScreen(
    account: Account
) {
    StoredViewModel<DetailAccountViewModel>(
        parameters = { parametersOf(account) }
    ) { viewModel ->
        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }
        val accountData by viewModel.accountData.collectAsState()
        val navController = LocalRootController.current.findRootController()
        val state = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                DetailAccountAppBar(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 1f - state.toolbarState.progress
                        },
                    color = accountData.color
                )
                DetailAccountExpandedAppBar(
                    modifier = Modifier
                        .parallax(0.4f),
                    contentAlpha = state.toolbarState.progress,
                    color = accountData.color,
                    amount = accountData.balance,
                    currency = accountData.currency,
                    iconId = accountData.iconId
                )

                AppBarIcon(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                        .statusBarsPadding()
                        .align(Alignment.TopStart),
                    painter = rememberVectorPainter("ic_arrow_back"),
                    onClick = { navController.popBackStack() },
                    tint = CoinTheme.color.primaryVariant
                )

                AccountNameText(
                    name = accountData.name,
                    state = state
                )

                EditButton(
                    state = state,
                    tint = accountData.color,
                    onClick = {
                        navController.push(
                            screen = MainNavigationTree.AddAccount.name,
                            params = account
                        )
                    }
                )
            }
        ) {
            val transactions by viewModel.transactions.collectAsState()
            CommonTransactionsList(transactions)
        }
    }
}