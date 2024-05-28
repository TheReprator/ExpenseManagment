package dev.reprator.appFeatures.api.performance

import kotlin.native.HiddenFromObjC

@HiddenFromObjC
fun interface Tracer {
    fun trace(
        name: String,
        block: () -> Unit,
    )
}
