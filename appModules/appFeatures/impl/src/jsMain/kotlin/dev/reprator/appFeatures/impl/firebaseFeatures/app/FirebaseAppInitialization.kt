package dev.reprator.appFeatures.impl.firebaseFeatures.app

import kotlin.js.json
import dev.reprator.appFeatures.impl.firebaseFeatures.app.external.FirebaseApp as JsFirebaseApp

class FirebaseModalApp internal constructor(private val js: JsFirebaseApp) {
    
     val options: FirebaseModalOptions
        get() = js.options.run {
            FirebaseModalOptions(appId, apiKey, databaseURL, storageBucket, projectId, messagingSenderId, authDomain, measurementId)
        }
    
    val config: FirebaseModalSetting
        get() = js.config.run {
            FirebaseModalSetting(name, automaticDataCollectionEnabled)
        }
}

internal fun FirebaseModalOptions.toJson() = json(
    "apiKey" to apiKey,
    "authDomain" to (authDomain ?: undefined),
    "databaseURL" to (databaseUrl ?: undefined),
    "projectId" to (projectId ?: undefined),
    "storageBucket" to (storageBucket ?: undefined),
    "messagingSenderId" to (gcmSenderId ?: undefined),
    "appId" to applicationId,
    "measurementId" to (measurementId ?: undefined),
)

private fun FirebaseModalSetting.toJson() = json(
    "name" to name,
    "automaticDataCollectionEnabled" to automaticDataCollectionEnabled
)

data class FirebaseModalOptions(
    val applicationId: String,
    val apiKey: String,
    val databaseUrl: String? = null,
    val storageBucket: String? = null,
    val projectId: String? = null,
    val gcmSenderId: String? = null,
    val authDomain: String? = null,
    val measurementId: String? = null
)

data class FirebaseModalSetting(
    val name: String="",
    val automaticDataCollectionEnabled: Boolean
)