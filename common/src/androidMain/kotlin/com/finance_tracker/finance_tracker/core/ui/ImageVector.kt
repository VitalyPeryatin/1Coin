package com.finance_tracker.finance_tracker.core.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource

@SuppressLint("DiscouragedApi")
@Composable
actual fun rememberImageVector(id: String): ImageVector {
    val context = LocalContext.current
    val imageVector = with(context) {
        ImageVector.vectorResource(resources.getIdentifier(id, "drawable", packageName))
    }
    return remember { imageVector }
}