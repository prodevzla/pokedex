package com.prodevzla.pokedex.presentation.drawer

import android.content.Context
import com.airbnb.android.showkase.models.Showkase
import com.prodevzla.pokedex.getBrowserIntent

fun handlePalette(context: Context, event: AppDrawerEvent) {
    if (event == AppDrawerEvent.ClickPalette) {
        context.startActivity(Showkase.getBrowserIntent(context))
    }
}
