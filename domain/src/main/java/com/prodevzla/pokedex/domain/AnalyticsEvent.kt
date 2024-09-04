package com.prodevzla.pokedex.domain

sealed class AnalyticsEvent {

    class ClickEvent(val name: String) : AnalyticsEvent()

}
