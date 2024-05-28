package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.InternetChecker
import dev.reprator.appFeatures.api.utility.NetworkListener
import dev.reprator.core.inject.ApplicationCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@Inject
@HiddenFromObjC
class InternetCheckerImpl(scope: ApplicationCoroutineScope, private val helper: NetworkListener): InternetChecker {
    
   override val networkStatus: StateFlow<Boolean> = callbackFlow {
        helper.registerListener(
            onNetworkAvailable = {
                trySend(true)
            },
            onNetworkLost = {
                trySend(false)
            }
        )

        awaitClose {
            helper.unregisterListener()
        }
        
    }.distinctUntilChanged().flowOn(Dispatchers.IO).stateIn(scope, SharingStarted.WhileSubscribed(), false)
    
}