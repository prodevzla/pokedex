package com.prodevzla.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prodevzla.pokedex.presentation.Home
import com.prodevzla.pokedex.presentation.Pokemon
import com.prodevzla.pokedex.presentation.list.ListScreen
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.navigationBarColor = this.getColor(R.color.black)

        setContent {
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
