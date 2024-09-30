package com.prodevzla.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.presentation.drawer.AppDrawer
import com.prodevzla.pokedex.presentation.list.ListScreen
import com.prodevzla.pokedex.presentation.navigation.Favourites
import com.prodevzla.pokedex.presentation.navigation.HomeRoute
import com.prodevzla.pokedex.presentation.navigation.PokemonDetailRoute
import com.prodevzla.pokedex.presentation.navigation.PokemonNavType
import com.prodevzla.pokedex.presentation.pokemonDetail.PokemonDetailScreen
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                    drawerContent = {
                        AppDrawer()
                    },
                ) {

                    SharedTransitionLayout {
                        NavHost(navController = navController, startDestination = HomeRoute) {
                            composable<HomeRoute> {
                                ListScreen(
                                    onClickPokemon = { pokemon ->
                                        navController.navigate(PokemonDetailRoute(
                                            id = pokemon.id,
                                        ))
                                    },
                                    onClickNavIcon = {
                                        toggleDrawer(scope, drawerState)
                                    },
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this
                                )
                            }
                            composable<PokemonDetailRoute>(
//                                typeMap = mapOf(
//                                    typeOf<Pokemon>() to PokemonNavType.PokemonType
//                                )
                            ) { backStackEntry ->
                                //val arguments = backStackEntry.toRoute<PokemonDetailRoute>()

                                PokemonDetailScreen(
                                    //pokemon = arguments.pokemon,
                                    onClickBack = {
                                        navController.navigateUp()
                                    }
                                )
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
