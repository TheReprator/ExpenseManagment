package dev.reprator.appFeatures.api.analytics

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "AccountBookAnalytics")
interface AppAnalytics {
    fun trackScreenView(
        name: String,
        arguments: Map<String, *>? = null,
    )

    fun setEnabled(enabled: Boolean)
}
