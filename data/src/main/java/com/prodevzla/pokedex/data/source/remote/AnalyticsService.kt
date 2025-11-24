package com.prodevzla.pokedex.data.source.remote

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
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
            event.value?.let {
                param(FirebaseAnalytics.Param.VALUE, it)
            }
        }

    }

//    TODO create new AnalyticsEvent for screen view e.g. ScreenViewEvent
//    private fun trackScreenView(event: AnalyticsEvent.ClickEvent) {
//        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//            param(FirebaseAnalytics.Param.ITEM_NAME, event.name)
//        }
//    }
}
