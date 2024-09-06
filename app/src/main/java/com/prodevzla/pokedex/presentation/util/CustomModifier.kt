package com.prodevzla.pokedex.presentation.util

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedElementTransition(key: String): Modifier {
    return this then Modifier.sharedElement(
        state = rememberSharedContentState(key = key),
        animatedVisibilityScope = this@AnimatedVisibilityScope,
    )
}
