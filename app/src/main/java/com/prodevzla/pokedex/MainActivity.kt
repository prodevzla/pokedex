package com.prodevzla.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
            val window: Window = this.window
            window.navigationBarColor = RoyalBlue.toArgb()

            PokedexTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = { AppDrawer() },
                ) {

                    Scaffold(bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            currentRoute = currentRoute
                        )
                    }) {
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
}

fun toggleDrawer(scope: CoroutineScope, drawerState: DrawerState) {
    scope.launch {
        if (drawerState.isClosed) drawerState.open() else drawerState.close()
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar(containerColor = RoyalBlue) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, "Home") },
            label = { Text(stringResource(R.string.home)) },
            selected = currentRoute == Home::class.java.name,
            onClick = { navController.navigate(Home) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, "Settings") },
            label = { Text(stringResource(R.string.favourites)) },
            selected = currentRoute == Favourites::class.java.name,
            onClick = { navController.navigate(Favourites) }
        )
    }
}
