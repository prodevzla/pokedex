@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package com.prodevzla.pokedex.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.Sort
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.LoadingScreen
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    onClickNavIcon: () -> Unit,
    onClickPokemon: (Pokemon) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val state by viewModel.uiState.collectAsState()

    val onEvent: (ListScreenEvent) -> Unit = remember(viewModel) {
        return@remember viewModel::onEvent
    }

    ListContent(
        state = state,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onEvent = {
            when (it) {
                //for these 2 events, we don't need to do anything on the viewModel apart from tracking with Firebase Analytics
                is ListScreenEvent.ClickPokemon -> onClickPokemon.invoke(it.pokemon)
                ListScreenEvent.ClickNavIcon -> onClickNavIcon()
                else -> Unit
            }
            onEvent.invoke(it)
        },
    )

}

//TODO use events pattern
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (ListScreenEvent) -> Unit = {}
) {

    var showSearchBar by rememberSaveable { mutableStateOf(false) }

    var showFilterDialog: Filter? by remember { mutableStateOf(null) }

    var showSortDialog by remember { mutableStateOf(false) }

    CustomScaffold(
        modifier = modifier,
        title = "Pokedex",
        navIcon = {
            IconButton(onClick = {
                onEvent(ListScreenEvent.ClickNavIcon)
            }) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        },
        actions = {
            if (state is ListState.Content) {
                IconButton(onClick = {
                    onEvent(ListScreenEvent.ClickSort)
                    showSortDialog = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        tint = if (state.sort.isDefault()) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            Color.Red
                        },
                        contentDescription = "menu"
                    )
                }
            }
        },
        floatingActionButton = {
            if (state is ListState.Content) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        onEvent(ListScreenEvent.ClickSearch)
                        showSearchBar = true
                    },
                ) {
                    Icon(Icons.Filled.Search, "search")
                }
            }
        }
    ) {

        when (state) {
            ListState.Loading -> LoadingScreen()
            ListState.Error -> ErrorScreen()
            is ListState.Content -> {

                val focusRequester = remember { FocusRequester() }

                //shall I use derivedState?
                var scrollToTop by remember { mutableStateOf(false) }

                val lazyListState = rememberLazyListState()

                AnimatedVisibility(showSearchBar) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.medium),
                        search = state.search,
                        onSearchChange = {
                            onEvent(ListScreenEvent.SearchPokemon(it))
                        },
                        focusRequester = focusRequester,
                        onClickClose = {
                            onEvent(ListScreenEvent.SearchPokemon(""))
                            showSearchBar = false
                        }
                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }

                state.filters?.let { filters ->
                    FiltersRow(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                        filters = filters,
                        onClickFilter = {
                            onEvent(ListScreenEvent.ClickFilter(it))
                            showFilterDialog = it
                        },
                    )
                }

                Text(
                    text = "count: ${state.pokemonList.count()}",
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                )

                LaunchedEffect(scrollToTop) {
                    if (scrollToTop) {
                        lazyListState.scrollToItem(0)
                        scrollToTop = false
                    }
                }

                LazyColumn(
                    state = lazyListState,
                    modifier = modifier,
                    contentPadding = PaddingValues(MaterialTheme.spacing.small),
                ) {
                    items(state.pokemonList, key = { it.id }) { item ->
                        with(sharedTransitionScope) {
                            with(animatedVisibilityScope) {
                                PokemonCard(
                                    pokemon = item,
                                    onClickPokemon = {
                                        onEvent(ListScreenEvent.ClickPokemon(it))
                                    },
                                )
                            }
                        }

                    }
                }

                showFilterDialog?.let { filter ->
                    FilterBottomSheet(
                        filter = filter,
                        onDismiss = {
                            showFilterDialog = null
                        },
                        onClickItem = { filterable, filterType ->
                            onEvent(ListScreenEvent.SelectFilter(filterable, filterType))
                            showFilterDialog = null
                            scrollToTop = true
                        }
                    )
                }

                if (showSortDialog) {
                    SortDialog(
                        onConfirm = { sort ->
                            onEvent(ListScreenEvent.SelectSort(sort))
                            showSortDialog = false
                            scrollToTop = true
                        },
                        value = state.sort,
                        onDismiss = { showSortDialog = false }
                    )
                }

            }
        }
    }

}

@ThemePreviews
@Composable
fun ListScreenPreview() {
    val state = ListState.Content(
        pokemonList = mutableListOf(
            Pokemon(
                id = 6885,
                name = "Charmander",
                types = listOf(
                    PokemonType(
                        id = 10,
                        name = UiText.DynamicString("Fire")
                    )
                ),
                generation = 1,
                //gameVersions = emptyList()
            ),
            Pokemon(
                id = 6886,
                name = "Charmeleon",
                types = listOf(
                    PokemonType(
                        id = 10,
                        name = UiText.DynamicString("Fire")
                    )
                ),
                generation = 1,
                //gameVersions = emptyList()
            ),
        ),
        filters = listOf(
            Filter(
                dialogTitle = UiText.DynamicString("Select generation"),
                weight = 1.0f,
                selection = 1,
                values = listOf(
                    PokemonGeneration(
                        id = 1,
                        name = UiText.DynamicString("Gen I")
                    )
                ),
                type = FilterType.GENERATION,
            ),
            Filter(
                dialogTitle = UiText.DynamicString("Select type"),
                weight = 1.0f,
                selection = 10,
                values = listOf(
                    PokemonType(
                        id = 10,
                        name = UiText.DynamicString("Fire")
                    )
                ),
                type = FilterType.TYPE,
            )
        ),
        sort = Sort(),
        search = ""
    )

    PokedexTheme {
        Surface {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    ListContent(
                        state = state,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun ListScreenLoadingPreview() {
    val state = ListState.Loading
    PokedexTheme {
        Surface {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    ListContent(
                        state = state,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
        }
    }
}
