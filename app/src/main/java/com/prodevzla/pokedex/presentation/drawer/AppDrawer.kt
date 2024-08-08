package com.prodevzla.pokedex.presentation.drawer

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.prodevzla.pokedex.presentation.util.ThemePreviews

@Composable
fun AppDrawer() {
    ModalDrawerSheet {
        Text("drawer content")
    }
}

@ThemePreviews
@Composable
fun AppDrawerPreview() {
    AppDrawer()
}
