package com.example.pasco
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.pasco.ui.CityApp
import com.example.pasco.ui.theme.PascoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PascoTheme {
                val windowSize = calculateWindowSizeClass(this)
                CityApp(windowSize = windowSize.widthSizeClass)
            }
        }
    }
}