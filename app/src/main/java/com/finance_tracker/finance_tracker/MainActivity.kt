package com.finance_tracker.finance_tracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.finance_tracker.finance_tracker.screens.container.Container
import com.finance_tracker.finance_tracker.theme.AppTheme
import com.finance_tracker.finance_tracker.theme.CoinTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE_SMS_PERMISSION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CoinTheme.color.background
                ) {
                    Container()
                }
            }
        }

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