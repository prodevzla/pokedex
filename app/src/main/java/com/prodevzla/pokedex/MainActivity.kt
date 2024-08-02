package com.prodevzla.pokedex

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prodevzla.pokedex.presentation.Home
import com.prodevzla.pokedex.presentation.Pokemon
import com.prodevzla.pokedex.presentation.list.ListScreen
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.RoyalBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val window: Window = this.window
            window.navigationBarColor = RoyalBlue.toArgb()
            PokedexTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Home) {
                    composable<Home> {
                        ListScreen()
                    }
                    composable<Pokemon> { backStackEntry ->
                        Text(text = "BYE RENZO")
                    }
                }
            }
        }
    }
}

