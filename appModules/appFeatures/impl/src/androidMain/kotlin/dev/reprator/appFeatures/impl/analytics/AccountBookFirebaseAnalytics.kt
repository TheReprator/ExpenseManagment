package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.Analytics
import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import me.tatarka.inject.annotations.Inject

@Inject
class AccountBookFirebaseAnalytics(
    private val context: Application,
) : Analytics {

    @delegate:SuppressLint("MissingPermission")
    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    override fun trackScreenView(
        name: String,
        arguments: Map<String, *>?,
    ) {
        try {
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, name)
                arguments?.let {
                    for (entry in arguments) {
                        putString("screen_arg_${entry.key}", entry.value.toString())
                    }
                }
            }

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        } catch (t: Throwable) {
            // Ignore, Firebase might not be setup for this project
        }
    }

    override fun setEnabled(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}
