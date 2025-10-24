package com.dapic.mobile.repairing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.dapic.mobile.repairing.ui.MainNavigation
import com.dapic.mobile.repairing.ui.theme.DapicRepairingTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Set status bar color programmatically (optional - theme method is preferred)
        // window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
        // WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        
        setContent {
            DapicRepairingTheme(
                dynamicColor = false
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainNavigation()
                    }
                }
            }
        }
    }
}
