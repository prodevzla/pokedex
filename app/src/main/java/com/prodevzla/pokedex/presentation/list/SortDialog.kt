package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Sort
import com.prodevzla.pokedex.domain.model.SortBy
import com.prodevzla.pokedex.domain.model.SortOrder
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.NeutralGrey
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun SortDialog(
    modifier: Modifier = Modifier,
    value: Sort,
    onConfirm: (Sort) -> Unit = { _ -> },
    onDismiss: () -> Unit = {}
) {
    val (selectedSortByOption: SortBy, onSortByOptionSelected: (SortBy) -> Unit) = remember { mutableStateOf(value.sortBy) }
    val (selectedOrderOption, onSortOrderOptionSelected) = remember { mutableStateOf(value.sortOrder) }

    val rbColors = RadioButtonColors(
        selectedColor = MaterialTheme.colorScheme.onSurface,
        unselectedColor = MaterialTheme.colorScheme.onSurface,
        disabledSelectedColor = MaterialTheme.colorScheme.onSurface,
        disabledUnselectedColor = MaterialTheme.colorScheme.onSurface
    )

    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.sort_dialog_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier.selectableGroup(),
            ) {
                SortBy.entries.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = it == selectedSortByOption,
                            onClick = { onSortByOptionSelected(it) },
                            colors = rbColors,
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.spacing.small),
                            text = it.name,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start
                        )
                    }

                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

                HorizontalDivider(color = NeutralGrey)

                Row(
                    modifier = Modifier.selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortOrder.entries.forEach {
                        RadioButton(
                            selected = it == selectedOrderOption,
                            onClick = { onSortOrderOptionSelected(it) },
                            colors = rbColors,
                        )
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }


                }

            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(Sort(selectedSortByOption, selectedOrderOption)) }) {
                Text(
                    text = stringResource(R.string.sort_dialog_button_confirm).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.sort_dialog_button_cancel).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@ThemePreviews
@Composable
fun SortDialogPreview() {
    PokedexTheme {
        Surface {
            SortDialog(
                value = Sort()
            )
        }
    }
}
