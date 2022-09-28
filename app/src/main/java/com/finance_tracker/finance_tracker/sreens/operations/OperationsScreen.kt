package com.finance_tracker.finance_tracker.sreens.operations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.sreens.transactions.TransactionsListScreen
import com.finance_tracker.finance_tracker.theme.AppColors

@Composable
fun OperationsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Purple700)
    ) {
        Text(
            text = stringResource(R.string.operations_screen_text),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        TransactionsListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun OperationsScreenPreview() {
    OperationsScreen()
}