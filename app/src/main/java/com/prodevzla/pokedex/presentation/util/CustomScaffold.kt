package com.prodevzla.pokedex.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navIcon: @Composable () -> Unit = {},
    topBarColor: Color = MaterialTheme.colorScheme.surface,
    actions: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    pullToRefreshEnabled: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = title,
                colors = TopAppBarColors(
                    containerColor = topBarColor,
                    scrolledContainerColor = Color.Black,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface

                ),
                navigationIcon = navIcon,
                actions = actions,
            )
        },
        floatingActionButton = floatingActionButton
    ) { innerPadding: PaddingValues ->

        if (pullToRefreshEnabled) {

            PullToRefreshBox(
                modifier = Modifier.padding(innerPadding),
                isRefreshing = isRefreshing,
                onRefresh = onRefresh

            ) {
                ColumnContent(
                    innerPadding = innerPadding,
                    pullToRefreshEnabled = true,
                    content = { content() }
                )
            }
            return@Scaffold
        }

        ColumnContent(
            innerPadding = innerPadding,
            pullToRefreshEnabled = false,
            content = { content() }
        )
    }


}


@Composable
fun ColumnContent(
    pullToRefreshEnabled: Boolean,
    innerPadding: PaddingValues,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (pullToRefreshEnabled) PaddingValues(0.dp) else innerPadding)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}

@ThemePreviews
@Composable
fun CustomScaffoldPreview() {
    PokedexTheme {
        CustomScaffold(modifier = Modifier, title = { Text("Pokedex") }) {
            Text(text = "Hello")
        }
    }
}
