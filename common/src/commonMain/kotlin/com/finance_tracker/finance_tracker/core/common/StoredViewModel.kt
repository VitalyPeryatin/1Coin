package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.KViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.java.KoinJavaComponent
import com.adeo.kviewmodel.odyssey.StoredViewModel as OdysseyStoredViewModel

@Composable
inline fun <reified T : KViewModel> StoredViewModel(
    noinline parameters: ParametersDefinition? = null,
    noinline content: @Composable (T) -> Unit
) {
    OdysseyStoredViewModel(
        factory = { KoinJavaComponent.getKoin().get(parameters = parameters) },
        content = content
    )
}