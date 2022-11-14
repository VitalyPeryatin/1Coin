package com.finance_tracker.finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.navigation.setupNavigation
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            setupNavigation(
                startScreen = MainNavigationTree.Main.name,
                onConfigure = {
                    setupWithActivity(this@MainActivity)
                }
            ) { navigationGraph() }
        }
    }
}