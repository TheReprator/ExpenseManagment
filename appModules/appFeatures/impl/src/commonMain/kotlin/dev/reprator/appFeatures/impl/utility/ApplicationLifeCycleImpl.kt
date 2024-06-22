package dev.reprator.appFeatures.impl.utility

import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import me.tatarka.inject.annotations.Inject

@Inject
class ApplicationLifeCycleImpl (private val initializers: Lazy<Set<ApplicationLifeCycle>>) :
    ApplicationLifeCycle {

    override fun isAppInForeGround() {
        println("vikram::ApplicationLifeCycle:: App is in foreground")
        initializers.value.forEach {
            it.isAppInForeGround()
        }
    }

    override fun isAppInBackground() {
        println("vikram::ApplicationLifeCycle:: App is in background")
        initializers.value.forEach {
            it.isAppInBackground()
        }
    }
}