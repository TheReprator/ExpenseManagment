package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import dev.reprator.appFeatures.api.utility.InternetChecker
import dev.reprator.appFeatures.api.utility.NetworkListener
import dev.reprator.core.inject.ApplicationCoroutineScope
import dev.reprator.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@Inject
@HiddenFromObjC
class InternetCheckerImpl(
    private val scope: ApplicationCoroutineScope,
    dispatchers: AppCoroutineDispatchers,
    private val helper: NetworkListener
) : InternetChecker, ApplicationLifeCycle {

    lateinit var job: Job
    
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

    }.distinctUntilChanged().flowOn(dispatchers.io).stateIn(scope, SharingStarted.WhileSubscribed(), false)

    override fun isAppInForeGround() {
        job = scope.launch {
            networkStatus.collect {
                println("vikram::InternetCheckerImpl:: Collect, ${networkStatus.value}, $it")
            }
        }
        println("vikram::InternetCheckerImpl:: App is in foreground, ${networkStatus.value}")
    }

    override fun isAppInBackground() {
        if(::job.isInitialized) {
            println("vikram::InternetCheckerImpl:: App is in background, job cancel")
            job.cancel()
        }
        println("vikram::InternetCheckerImpl:: App is in background, ${networkStatus.value}")
    }

}