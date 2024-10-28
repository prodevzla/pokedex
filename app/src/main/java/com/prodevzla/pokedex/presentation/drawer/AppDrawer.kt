package com.prodevzla.pokedex.presentation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun AppDrawer(onClick: (AppDrawerEvent) -> Unit) {
    ModalDrawerSheet {
        Column {
            DrawerItem(
                label = "Favourites",
                event = AppDrawerEvent.ClickFavourites,
                onClick = onClick
            )
            HorizontalDivider(modifier = Modifier.height(1.dp))

            DrawerItem(
                label = "Abilities",
                event = AppDrawerEvent.ClickAbilities,
                onClick = onClick
            )
            HorizontalDivider(modifier = Modifier.height(1.dp))

            DrawerItem(
                label = "Settings",
                event = AppDrawerEvent.ClickSettings,
                onClick = onClick
            )
            HorizontalDivider(modifier = Modifier.height(1.dp))

            DrawerItem(
                label = "Palette",
                event = AppDrawerEvent.ClickPalette,
                onClick = onClick
            )
            HorizontalDivider(modifier = Modifier.height(1.dp))

        }
    }
}

@Composable
fun DrawerItem(label: String, event: AppDrawerEvent, onClick: (AppDrawerEvent) -> Unit) {
    ListItem(
        modifier = Modifier.clickable {
            onClick.invoke(event)
        },
        headlineContent = {
            Text(
                label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
    )
}

@ThemePreviews
@Composable
private fun AppDrawerPreview() {
    PokedexTheme {
        Surface {
            AppDrawer({})
        }
    }
}
