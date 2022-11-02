package com.finance_tracker.finance_tracker.core.common

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ParcelizeStub

actual typealias Parcelize = ParcelizeStub
actual interface Parcelable