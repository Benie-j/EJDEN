package com.ejden.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ejden.shared.EjdenApp

class MainActivity : ComponentActivity() {

    private val preferences by lazy {
        getSharedPreferences("ejden_preferences", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialTheme = preferences.getString("theme_mode", "system") ?: "system"

        setContent {
            EjdenApp(
                initialTheme = initialTheme,
                onThemeChanged = { selected ->
                    preferences.edit().putString("theme_mode", selected).apply()
                }
            )
        }
    }
}
