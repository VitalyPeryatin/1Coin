package com.finance_tracker.finance_tracker

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Main : NavigationItem("main", R.drawable.ic_main, "Главная")
    object Operations : NavigationItem("operations", R.drawable.ic_operations, "Операции")
    object Else : NavigationItem("else", R.drawable.ic_operations, "Ещё")
}
