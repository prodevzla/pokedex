package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterDefault
import com.prodevzla.pokedex.domain.model.FilterOption
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.GameVersionGroup
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.asString
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    filter: Filter,
    onDismiss: () -> Unit = {},
    onClickItem: () -> Unit = {}
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        FilterSheetContent(
            filter = filter,
            onClickItem = onClickItem
        )
    }
}

@Composable
fun FilterSheetContent(
    modifier: Modifier = Modifier,
    filter: Filter,
    onClickItem: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = filter.title.asString(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        when (filter.filterOption) {
            FilterOption.VERSION -> {
                filter.values.filterIsInstance<FilterDefault>().map {
                    item {
                        FilterOptionsDefault(
                            item = it,
                            filter = filter,
                            onClickItem = onClickItem
                        )
                    }
                }

                filter.values.filterIsInstance<GameVersionGroup>().groupBy { it.generation }.map {
                    item { FilterOptionsVersions(it, filter = filter, onClickItem = onClickItem) }
                }

            }

            FilterOption.GENERATION,
            FilterOption.TYPE ->
                items(filter.values) {
                    FilterOptionsDefault(
                        item = it,
                        filter = filter,
                        onClickItem = onClickItem
                    )
                }
        }

    }
}

@Composable
fun FilterOptionsDefault(item: Filterable, filter: Filter, onClickItem: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            filter.onClickSelection.invoke(item.id)
            onClickItem()
        },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = item.getColor()
        ),
    ) {
        Text(
            text = item.name.asString().uppercase(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun FilterOptionsVersions(entry: Map.Entry<Int, List<GameVersionGroup>>, filter: Filter, onClickItem: () -> Unit) {
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
    Text(
        text = UiText.StringResource(R.string.gen, entry.key).asString(),
        style = MaterialTheme.typography.titleMedium
    )

    Column {
        entry.value.forEach { versionGroup ->
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                val versions = versionGroup.versions.count()
                val weight = 1.toFloat().div(versions)

                versionGroup.versions.forEach { version ->
                    Button(
                        onClick = {
                            filter.onClickSelection.invoke(versionGroup.id)
                            onClickItem()
                        },
                        modifier = Modifier.weight(weight = weight),
                        shape = RectangleShape,
                    )
                    {
                        Text(
                            text = version.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}


@ThemePreviews
@Composable
fun FilterTypeBottomSheetPreview() {
    PokedexTheme {
        Surface {
            FilterSheetContent(
                filter = Filter(
                    title = UiText.DynamicString("Select type"),
                    weight = 1f,
                    selection = PokemonType(id = 1, name = UiText.DynamicString("normal")),
                    values = listOf(
                        PokemonType(id = 1, name = UiText.DynamicString("normal")),
                        PokemonType(id = 2, name = UiText.DynamicString("fighting")),
                        PokemonType(id = 3, name = UiText.DynamicString("flying")),
                        PokemonType(id = 4, name = UiText.DynamicString("poison"))
                    ),
                    onClickSelection = {},
                    filterOption = FilterOption.TYPE,
                )
            )
        }
    }
}
