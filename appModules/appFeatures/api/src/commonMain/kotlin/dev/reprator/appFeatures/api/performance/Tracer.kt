package dev.reprator.appFeatures.api.performance

import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
fun interface Tracer {
    fun trace(
        name: String,
        block: () -> Unit,
    )
}
