package com.prodevzla.pokedex.data.source.remote

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import com.prodevzla.pokedex.domain.AnalyticsEvent

class AnalyticsService {

    fun trackEvent(event: AnalyticsEvent) {

        when (event) {
            is AnalyticsEvent.ClickEvent -> trackClickEvent(event)
        }

    }

    private fun trackClickEvent(event: AnalyticsEvent.ClickEvent) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_NAME, event.name)
        }

    }

//    TODO create new AnalyticsEvent for screen view e.g. ScreenViewEvent
//    private fun trackScreenView(event: AnalyticsEvent.ClickEvent) {
//        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//            param(FirebaseAnalytics.Param.ITEM_NAME, event.name)
//        }
//    }
}
