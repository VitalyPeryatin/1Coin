package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.useResource
import org.xml.sax.InputSource

@Composable
actual fun rememberImageVector(id: String): ImageVector {
    val density = LocalDensity.current
    return remember(id) {
        useResource("drawable/$id.xml") { stream ->
            loadXmlImageVector(InputSource(stream), density)
        }
    }
}