package com.finance_tracker.finance_tracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.theme.setupThemedNavigation

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE_SMS_PERMISSION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupThemedNavigation(MainNavigationTree.Main.name) { navigationGraph() }

        requestSmsPermission()
    }

    // TODO: Запрос разрешений перенести в место настройки СМС сообщений
    private fun requestSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }
}