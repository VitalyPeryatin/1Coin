package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton

@Composable
fun AddButtonSection(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onAddClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(CoinTheme.color.background),
        elevation = 8.dp
    ) {
        PrimaryButton(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(16.dp),
            text = "Add",
            onClick = onAddClick,
            enabled = enabled
        )
    }
}