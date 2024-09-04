package com.prodevzla.pokedex.data.repository

import com.prodevzla.pokedex.data.source.remote.AnalyticsService
import com.prodevzla.pokedex.domain.AnalyticsEvent
import com.prodevzla.pokedex.domain.repository.AnalyticsRepository
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val analyticsService: AnalyticsService
): AnalyticsRepository {

    override fun trackEvent(event: AnalyticsEvent) {
        analyticsService.trackEvent(event)
    }

}
