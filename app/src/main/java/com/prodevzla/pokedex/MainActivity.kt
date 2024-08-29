package com.prodevzla.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prodevzla.pokedex.presentation.Favourites
import com.prodevzla.pokedex.presentation.Home
import com.prodevzla.pokedex.presentation.Pokemon
import com.prodevzla.pokedex.presentation.drawer.AppDrawer
import com.prodevzla.pokedex.presentation.list.ListScreen
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.RoyalBlue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
//            val window: Window = this.window
//            window.navigationBarColor = RoyalBlue.toArgb()

            PokedexTheme {
                val navController = rememberNavController()

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = { AppDrawer() },
                ) {
                    NavHost(navController = navController, startDestination = Home) {
                        composable<Home> {
                            ListScreen(
                                onClickPokemon = { navController.navigate(Pokemon) },
                                onClickNavIcon = { toggleDrawer(scope, drawerState) }
                            )
                        }
                        composable<Pokemon> { backStackEntry ->
                            Text(text = "Pokemon Details")
                        }

                        composable<Favourites> {
                            Text(text = "Favourites")
                        }
                    }
                }

            }
        }
    }
}

fun toggleDrawer(scope: CoroutineScope, drawerState: DrawerState) {
    scope.launch {
        if (drawerState.isClosed) drawerState.open() else drawerState.close()
    }
}
