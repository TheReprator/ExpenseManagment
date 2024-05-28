package dev.reprator.appFeatures.api.utility

import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
interface InternetChecker {
    val networkStatus: StateFlow<Boolean>
}