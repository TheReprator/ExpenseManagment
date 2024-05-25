package dev.reprator.accountbook

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import kotlinx.coroutines.launch


class ApplicaitonLifeCycleObserver(private val lifeCycle: ApplicationLifeCycle) : DefaultLifecycleObserver {
    
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        lifeCycle.isAppInForeGround()
    }

    override fun onStop(owner: LifecycleOwner) {
        lifeCycle.isAppInBackground()
        super.onStop(owner)
    }
}
