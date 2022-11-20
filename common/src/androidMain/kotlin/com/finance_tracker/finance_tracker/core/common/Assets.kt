package com.finance_tracker.finance_tracker.core.common

actual fun getRaw(context: Context, name: String, ext: String): String {
    val identifier = context.resources.getIdentifier(name, "raw", context.packageName)
    return context.resources.openRawResource(identifier)
        .bufferedReader()
        .readText()
}