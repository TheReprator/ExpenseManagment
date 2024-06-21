package dev.reprator.appFeatures.impl.firebaseFeatures.analytics

import dev.reprator.appFeatures.impl.firebaseFeatures.analytics.external.Analytics
import dev.reprator.appFeatures.impl.firebaseFeatures.analytics.external.AnalyticsCallOptions
import dev.reprator.appFeatures.impl.firebaseFeatures.analytics.external.logEvent
import dev.reprator.appFeatures.impl.firebaseFeatures.jsonWithoutNullValues
import kotlin.js.Json

class FirebaseModalAnalytics internal constructor(private val wrapped: Analytics) {

    sealed class Event(val name: String) {
        internal abstract fun toParams(): Json

        /** Represents a screen within an app; one page might host multiple screens */
        // https://firebase.google.com/docs/reference/js/analytics#logevent_3
        class PageView(private val title: String? = null, private val location: String? = null, private val path: String? = null) : Event("page_view") {
            override fun toParams() = jsonWithoutNullValues(
                "title" to title,
                "location" to location,
                "path" to path,
            )
        }
        
        class ScreenView(private val screenName: String, private val screenClass: String) : Event("screen_view") {
            override fun toParams() = jsonWithoutNullValues(
                "screen_name" to screenName,
                "screen_class" to screenClass
            )
        }

        // region games

        // https://developers.google.com/analytics/devguides/collection/ga4/reference/events?client_type=gtag#level_start
        class LevelStart(private val levelName: String? = null) : Event("level_start") {
            override fun toParams() = jsonWithoutNullValues(
                "level_name" to levelName,
            )
        }

        // https://developers.google.com/analytics/devguides/collection/ga4/reference/events?client_type=gtag#level_start
        class LevelEnd(private val levelName: String? = null, private val success: Boolean? = null) : Event("level_end") {
            override fun toParams() = jsonWithoutNullValues(
                "level_name" to levelName,
                "success" to success,
            )
        }

        // endregion
    }

    fun log(event: Event, options: FirebaseModalAnalyticsCallOptions? = null) {
        logEvent(
            wrapped,
            event.name,
            event.toParams(),
            @Suppress("NAME_SHADOWING")
            options?.let { options ->
            object : AnalyticsCallOptions {
                override val global get() = options.global
            }
        })
    }

    fun setAnalyticsCollectionEnabled(enabled: Boolean) {
        dev.reprator.appFeatures.impl.firebaseFeatures.analytics.external.setAnalyticsCollectionEnabled(wrapped, enabled)
    }
    
    fun setUserId(userId: String) {
        dev.reprator.appFeatures.impl.firebaseFeatures.analytics.external.setUserId(wrapped, userId)
    }
}

data class FirebaseModalAnalyticsCallOptions(val global: Boolean)

// TODO: Support passing in EventParams as well.
class FirebaseModalAnalyticsSettings(internal val gtagParams: FirebaseModalGtagConfigParams = FirebaseModalGtagConfigParams())

// https://firebase.google.com/docs/reference/js/analytics.gtagconfigparams
data class FirebaseModalGtagConfigParams(
    val allowAdPersonalizationSignals: Boolean? = null,
    val allowGoogleSignals: Boolean? = null,
    val cookieDomain: String? = null,
    val cookieExpires: Number? = null,
    val cookieFlags: String? = null,
    val cookiePrefix: String? = null,
    val cookieUpdate: Boolean? = null,
    val pageLocation: String? = null,
    val pageTitle: String? = null,
    val sendPageView: Boolean? = null,
) {
    internal fun toJson() = jsonWithoutNullValues(
        "allow_ad_personalization_signals" to allowAdPersonalizationSignals,
        "allow_google_signals" to allowGoogleSignals,
        "cookie_domain" to cookieDomain,
        "cookie_expires" to cookieExpires,
        "cookie_flags" to cookieFlags,
        "cookie_prefix" to cookiePrefix,
        "cookie_update" to cookieUpdate,
        "page_location" to pageLocation,
        "page_title" to pageTitle,
        "send_page_view" to sendPageView,
    )
}