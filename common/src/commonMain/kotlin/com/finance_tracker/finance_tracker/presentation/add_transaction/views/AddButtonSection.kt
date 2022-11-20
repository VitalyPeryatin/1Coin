package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun ActionButtonsSection(
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    enabled: Boolean = true,
    onAddClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onDuplicateClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(CoinTheme.color.background),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isEditMode) {
                IconActionButton(
                    painter = rememberVectorPainter("ic_delete"),
                    tint = CoinTheme.color.accentRed,
                    onClick = onDeleteClick
                )
                IconActionButton(
                    painter = rememberVectorPainter("ic_duplicate"),
                    tint = CoinTheme.color.content,
                    onClick = onDuplicateClick
                )
            }

            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = if (isEditMode) {
                    stringResource("add_transaction_btn_edit")
                } else {
                    stringResource("add_transaction_btn_add")
                },
                onClick = {
                    if (isEditMode) {
                        onEditClick.invoke()
                    } else {
                        onAddClick.invoke()
                    }
                },
                enabled = enabled
            )
        }
    }
}

@Composable
private fun IconActionButton(
    painter: Painter,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CoinTheme.color.secondaryBackground)
            .clickable { onClick.invoke() }
            .padding(8.dp),
        painter = painter,
        tint = tint,
        contentDescription = null
    )
}