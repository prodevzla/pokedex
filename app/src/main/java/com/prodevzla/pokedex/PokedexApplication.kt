package com.prodevzla.pokedex

import android.app.Application
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokedexApplication: Application()

@ShowkaseRoot
class MyRootModule: ShowkaseRootModule
