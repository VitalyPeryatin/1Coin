package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton

@Composable
fun AddButtonSection(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
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
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(16.dp),
            text = "Add",
            onClick = onAddClick
        )
    }
}

@Preview
@Composable
fun AddButtonSectionPreview() {
    AddButtonSection()
}