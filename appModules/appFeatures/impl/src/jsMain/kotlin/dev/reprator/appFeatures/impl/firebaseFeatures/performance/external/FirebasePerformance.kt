@file:JsModule("firebase/performance")
@file:JsNonModule

package dev.reprator.appFeatures.impl.firebaseFeatures.performance.external

import dev.reprator.appFeatures.impl.firebaseFeatures.app.external.FirebaseApp

external fun getPerformance(app: FirebaseApp? = definedExternally): FirebasePerformance

internal external fun trace(performance: FirebasePerformance, name: String): PerformanceTrace

external interface FirebasePerformance {
    var app: FirebaseApp
    var dataCollectionEnabled: Boolean
    var instrumentationEnabled: Boolean
}

external interface PerformanceTrace {
    fun getAttribute(attr: String): String?
    
    fun getAttributes(): Map<String, String>
    fun getMetric(metricName: String): Int
    fun incrementMetric(metricName: String, num: Int? = definedExternally)
    fun putAttribute(attr: String, value: String)
    fun putMetric(metricName: String, num: Int)
    fun removeAttribute(attr: String)
    
    fun start()
    fun stop()
}