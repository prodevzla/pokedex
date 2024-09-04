@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.prodevzla.pokedex.presentation.list

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterOption
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
    val context = LocalContext.current

    val state by viewModel.uiState.collectAsState()

    val onSortChange: (Sort) -> Unit = remember(viewModel) {
        return@remember viewModel::onSortChange
    }

    val onSearchChange: (String) -> Unit = remember(viewModel) {
        return@remember viewModel::onSearchChange
    }

    ListContent(
        state = state,
        context = context,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onClickNavIcon = onClickNavIcon,
        onClickPokemon = onClickPokemon,
        onSortChange = onSortChange,
        onSearchChange = onSearchChange,
    )

}

//TODO use events pattern
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListState,
    context: Context,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClickNavIcon: () -> Unit = {},
    onClickPokemon: (Pokemon) -> Unit = {},
    onSortChange: (Sort) -> Unit = {},
    onSearchChange: (String) -> Unit = {},
) {

    var showSearchBar by rememberSaveable { mutableStateOf(false) }

    var showFilterDialog: Filter? by remember { mutableStateOf(null) }

    var showSortDialog by remember { mutableStateOf(false) }

    CustomScaffold(
        modifier = modifier,
        title = "Pokedex",
        navIcon = {
            IconButton(onClick = onClickNavIcon) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        },
        actions = {
            if (state is ListState.Content) {
                IconButton(onClick = {
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
                        showSearchBar = true
                    },
                ) {
                    Icon(Icons.Filled.Search, "search.")
                }
            }
        }
    ) {


        when (state) {
            ListState.Loading -> LoadingScreen()
            ListState.Error -> ErrorScreen()
            is ListState.Content -> {

                val focusRequester = remember { FocusRequester() }

                var scrollToTop by remember { mutableStateOf(false) }

                val lazyListState = rememberLazyListState()

                AnimatedVisibility(showSearchBar) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.medium),
                        search = state.search,
                        onSearchChange = onSearchChange,
                        focusRequester = focusRequester,
                        onClickClose = {
                            onSearchChange("")
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
                        PokemonCard(
                            context = context,
                            pokemon = item,
                            onClickPokemon = onClickPokemon,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }

                showFilterDialog?.let { filter ->
                    FilterBottomSheet(
                        filter = filter,
                        onDismiss = {
                            showFilterDialog = null
                        },
                        onClickItem = {
                            showFilterDialog = null
                            scrollToTop = true
                        }
                    )
                }

                if (showSortDialog) {
                    SortDialog(
                        onConfirm = { sort ->
                            onSortChange.invoke(sort)
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
                types = emptyList(),
                generation = 1,
                //gameVersions = emptyList()
            ),
            Pokemon(
                id = 6886,
                name = "Charmeleon",
                types = emptyList(),
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
                onClickSelection = {},
                filterOption = FilterOption.GENERATION,
            ),
            Filter(
                dialogTitle = UiText.DynamicString("Select type"),
                weight = 1.0f,
                selection = 1,
                values = listOf(
                    PokemonType(
                        id = 10,
                        name = UiText.DynamicString("Fire")
                    )
                ),
                onClickSelection = {},
                filterOption = FilterOption.TYPE,
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
                        context = LocalContext.current,
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
                        context = LocalContext.current,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
        }
    }
}
