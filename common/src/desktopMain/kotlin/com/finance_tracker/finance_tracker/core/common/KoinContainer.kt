package com.finance_tracker.finance_tracker.core.common

import org.koin.core.Koin
import org.koin.core.KoinApplication

object KoinContainer {
    var koinApp : KoinApplication? = null
}

val globalKoin: Koin
    get() = KoinContainer.koinApp?.koin!!