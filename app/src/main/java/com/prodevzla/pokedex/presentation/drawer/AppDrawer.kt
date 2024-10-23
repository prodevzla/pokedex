package com.prodevzla.pokedex.presentation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
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
            ListItem(
                modifier = Modifier.clickable {
                    onClick.invoke(AppDrawerEvent.ClickAbilities)
                },
                headlineContent = {
                    Text("Abilities")
                },
            )
            HorizontalDivider(modifier = Modifier.height(1.dp))
            ListItem(
                modifier = Modifier.clickable {
                    onClick.invoke(AppDrawerEvent.ClickSettings)
                },
                headlineContent = {
                    Text("Settings")
                },
            )
            HorizontalDivider(modifier = Modifier.height(1.dp))
        }
    }
}

@ThemePreviews
@Composable
fun AppDrawerPreview() {
    PokedexTheme { 
        Surface {
            AppDrawer({})
        }
    }
}

//prepare query with all the abilities
//create events sealed class for this screen
