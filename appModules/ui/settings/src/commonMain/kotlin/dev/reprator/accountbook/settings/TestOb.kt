package dev.reprator.accountbook.settings

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class TestOb: DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        println("Vikram::LifeCycle::settings, onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        println("Vikram::LifeCycle::settings, onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        println("Vikram::LifeCycle::settings, onResume")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        println("Vikram::LifeCycle::settings, onDestroy")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        println("Vikram::LifeCycle::settings, onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        println("Vikram::LifeCycle::settings, onStop")
    }
}