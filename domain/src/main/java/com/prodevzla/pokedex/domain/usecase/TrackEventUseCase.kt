package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.AnalyticsEvent
import com.prodevzla.pokedex.domain.repository.AnalyticsRepository

class TrackEventUseCase (private val trackerRepository: AnalyticsRepository) {

    operator fun invoke(event: AnalyticsEvent) {
        trackerRepository.trackEvent(event)
    }
}
