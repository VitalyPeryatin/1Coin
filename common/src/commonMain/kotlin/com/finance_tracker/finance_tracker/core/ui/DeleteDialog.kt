package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun DeleteDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 24.dp),
            text = "${stringResource("dialog_title_delete")} $titleEntity",
            style = CoinTheme.typography.h4
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            text = stringResource("dialog_body_delete"),
            style = CoinTheme.typography.body2
        )
        Row(
            modifier = Modifier.padding(top = 24.dp)
        ) {
            ActionButton(
                text = stringResource("btn_cancel"),
                modifier = Modifier
                    .weight(1f),
                textColor = CoinTheme.color.primary,
                onClick = { onCancelClick.invoke() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            ActionButton(
                text = stringResource("btn_delete"),
                modifier = Modifier
                    .weight(1f),
                textColor = CoinTheme.color.accentRed,
                onClick = { onDeleteClick.invoke() }
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = CoinTheme.color.content,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick.invoke() }
            .border(
                width = 1.dp,
                color = CoinTheme.color.dividers,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        text = text,
        style = CoinTheme.typography.body2_medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = textColor,
        textAlign = TextAlign.Center
    )
}