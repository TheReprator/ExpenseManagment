package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import me.tatarka.inject.annotations.Inject

@Inject
class ApplicationLifeCycleImpl constructor(): ApplicationLifeCycle {

    override fun isAppInForeGround() {
        println("vikram123:: App is in foreground")
    }

    override fun isAppInBackground() {
        println("vikram123:: App is in background")
    }
}