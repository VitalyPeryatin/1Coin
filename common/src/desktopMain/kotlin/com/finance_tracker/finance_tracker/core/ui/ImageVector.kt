package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.useResource
import org.xml.sax.InputSource

@Composable
actual fun loadXmlPicture(name: String): ImageVector {
    return useResource("drawable/$name.xml") { stream ->
        loadXmlImageVector(InputSource(stream), LocalDensity.current)
    }
}