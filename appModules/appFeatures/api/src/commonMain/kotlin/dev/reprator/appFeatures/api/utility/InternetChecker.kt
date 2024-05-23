package dev.reprator.appFeatures.api.utility

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "AccountBookInternetChecker")
interface InternetChecker {
    val isInternetAvailable: Boolean
    
    fun startObserving()
    fun stopObserving()
}