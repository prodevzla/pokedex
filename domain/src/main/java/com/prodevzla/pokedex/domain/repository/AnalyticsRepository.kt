package com.prodevzla.pokedex.domain.repository

import com.prodevzla.pokedex.domain.AnalyticsEvent

interface AnalyticsRepository {

    fun trackEvent(event: AnalyticsEvent)

}
