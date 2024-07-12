package com.prodevzla.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prodevzla.pokedex.ui.screens.ListScreen
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
                    composable<Profile> { backStackEntry ->
                        Text(text = "BYE RENZO")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        Greeting("Android")
    }
}

// Define a home destination that doesn't take any arguments
@kotlinx.serialization.Serializable
object Home

// Define a profile destination that takes an ID
@kotlinx.serialization.Serializable
data class Profile(val id: String)

//Add dependencies
//navigation compose
//hilt
//retrofit
