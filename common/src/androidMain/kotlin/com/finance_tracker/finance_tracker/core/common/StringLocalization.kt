package com.finance_tracker.finance_tracker.core.common

import android.annotation.SuppressLint

@SuppressLint("DiscouragedApi")
actual fun getLocalizedString(id: String, context: Context): String {
    return with(context) {
        resources.getString(resources.getIdentifier(id, "string", packageName))
    }
}