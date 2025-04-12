package com.amits.quickuserapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.amits.quickuserapp.ui.composable.UserAppScreen
import com.amits.quickuserapp.ui.theme.QuickUserAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickUserAppTheme {
                // Use the innerPadding from Scaffold
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserAppScreen(modifier = Modifier.padding(innerPadding)) // Apply innerPadding
                }
            }
        }
    }
}