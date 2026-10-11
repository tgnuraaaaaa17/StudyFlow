package com.example.deadliner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.deadliner.ui.AppNavigation
import com.example.deadliner.ui.theme.DeadlinerTheme

private const val TAG = "Lifecycle"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate")
        enableEdgeToEdge()
        setContent {
            DeadlinerTheme {
                AppNavigation()
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "MainActivity onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "MainActivity onResume") }
    override fun onPause() { Log.d(TAG, "MainActivity onPause"); super.onPause() }
    override fun onStop() { Log.d(TAG, "MainActivity onStop"); super.onStop() }
    override fun onDestroy() { Log.d(TAG, "MainActivity onDestroy"); super.onDestroy() }
}