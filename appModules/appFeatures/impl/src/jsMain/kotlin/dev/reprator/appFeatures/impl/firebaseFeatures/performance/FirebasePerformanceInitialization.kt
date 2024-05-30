package dev.reprator.appFeatures.impl.firebaseFeatures.performance

import dev.reprator.appFeatures.impl.firebaseFeatures.performance.external.FirebasePerformance
import dev.reprator.appFeatures.impl.firebaseFeatures.performance.external.PerformanceTrace
import dev.reprator.appFeatures.impl.firebaseFeatures.performance.external.trace


class FirebaseModalPerformance internal constructor(val js: FirebasePerformance) {

    fun newTrace(traceName: String): FirebaseModalTrace = rethrow {
        FirebaseModalTrace(trace(js, traceName))
    }

    fun isPerformanceCollectionEnabled(): Boolean = js.dataCollectionEnabled

    fun setPerformanceCollectionEnabled(enable: Boolean) {
        js.dataCollectionEnabled = enable
    }

    fun isInstrumentationEnabled(): Boolean = js.instrumentationEnabled

    fun setInstrumentationEnabled(enable: Boolean) {
        js.instrumentationEnabled = enable
    }
}

class FirebaseModalTrace internal constructor(private val js: PerformanceTrace) {

    fun start() = rethrow { js.start() }
    fun stop() = rethrow { js.stop() }
    fun getLongMetric(metricName: String) = rethrow { js.getMetric(metricName).toLong() }
    fun incrementMetric(metricName: String, incrementBy: Long) =
        rethrow { js.incrementMetric(metricName, incrementBy.toInt()) }

    fun putMetric(metricName: String, value: Long) = rethrow { js.putMetric(metricName, value.toInt()) }
    fun getAttribute(attribute: String): String? = rethrow { js.getAttribute(attribute) }
    fun putAttribute(attribute: String, value: String) = rethrow { js.putAttribute(attribute, value) }
    fun removeAttribute(attribute: String) = rethrow { js.removeAttribute(attribute) }

    fun primitiveHashMap(container: dynamic): HashMap<String, String> {
        val m = HashMap<String, String>().asDynamic()
        m.map = container
        val keys = js("Object.keys")
        m.`$size` = keys(container).length
        return m
    }
}


internal inline fun <R> rethrow(function: () -> R): R {
    try {
        return function()
    } catch (e: Exception) {
        throw e
    } catch (e: dynamic) {
        errorToException(e)
        throw e
    }
}

internal fun errorToException(error: dynamic) = (error?.code ?: error?.message ?: "")
    .toString()
    .lowercase()
    .let { code ->
        when {
            else -> {
                println("Unknown error code in ${JSON.stringify(error)}")
            }
        }
    }
