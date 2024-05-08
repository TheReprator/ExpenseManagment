package dev.reprator.appFeatures.api.performance

fun interface Tracer {
    fun trace(
        name: String,
        block: () -> Unit,
    )
}
