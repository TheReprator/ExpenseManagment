package dev.reprator.accountbook.appinitializers

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import dev.reprator.appFeatures.api.utility.InternetChecker
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class AppComponentInitializer(private val internetChecker: InternetChecker) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        owner.lifecycle.coroutineScope.launch {
           // internetChecker.startObserving()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        //internetChecker.stopObserving()
        super.onPause(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        //internetChecker.stopObserving()
        super.onStop(owner)
    }
}
