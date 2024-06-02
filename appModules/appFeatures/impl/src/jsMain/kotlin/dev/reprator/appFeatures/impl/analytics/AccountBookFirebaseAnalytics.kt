package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.Analytics
import dev.reprator.appFeatures.impl.firebaseFeatures.analytics.FirebaseModalAnalytics
import dev.reprator.appFeatures.impl.firebaseFeatures.analytics.FirebaseModalAnalyticsCallOptions
import dev.reprator.appFeatures.impl.firebaseFeatures.app.external.FirebaseApp
import me.tatarka.inject.annotations.Inject

@Inject
class AccountBookFirebaseAnalytics(
    private val app: FirebaseApp,
    private val analytics: FirebaseModalAnalytics
) : Analytics {

    override fun trackScreenView(
        name: String,
        arguments: Map<String, *>?,
    ) {
        arguments?.let {
            for (entry in it) {
                val event = FirebaseModalAnalytics.Event.ScreenView("screen_arg_${entry.key}", entry.value.toString())
                analytics.log(event)
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        analytics.setAnalyticsCollectionEnabled(enabled)
    }
}
