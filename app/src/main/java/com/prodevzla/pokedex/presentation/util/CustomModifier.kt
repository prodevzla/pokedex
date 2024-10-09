package com.prodevzla.pokedex.presentation.util

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//@OptIn(ExperimentalSharedTransitionApi::class)
//@Composable
//fun Modifier.sharedElementTransition(
//    key: String,
//    sharedTransitionScope: SharedTransitionScope?,
//    animatedVisibilityScope: AnimatedVisibilityScope?,
//): Modifier {
//    if (sharedTransitionScope != null && animatedVisibilityScope != null) {
//        with(sharedTransitionScope) {
//
//            return Modifier.sharedElement(
//                state = rememberSharedContentState(key = key),
//                animatedVisibilityScope = animatedVisibilityScope,
//            )
//        }
//    } else {
//        return this
//    }
//
//}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedElementTransition(
    key: String,
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: AnimatedVisibilityScope?,
): Modifier {
    if (sharedTransitionScope != null && animatedVisibilityScope != null) {
        with(sharedTransitionScope) {
            //then is applied to the Modifier we are extending
            return then(
                Modifier.sharedElement(
                    state = rememberSharedContentState(key = key),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            )
        }
    }
    return this
}
