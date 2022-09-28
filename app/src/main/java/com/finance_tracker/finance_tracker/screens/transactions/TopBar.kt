package com.finance_tracker.finance_tracker.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(
            text = "Transactions",
            color = CoinTheme.color.textColor,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
            contentDescription = "Calendar",
            tint = CoinTheme.color.iconColor
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar()
}