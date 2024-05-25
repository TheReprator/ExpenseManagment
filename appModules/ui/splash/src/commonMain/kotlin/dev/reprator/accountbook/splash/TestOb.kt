package dev.reprator.accountbook.splash

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class TestOb: DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        println("Vikram::LifeCycle::splash, onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        println("Vikram::LifeCycle::splash, onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        println("Vikram::LifeCycle::splash, onResume")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        println("Vikram::LifeCycle::splash, onDestroy")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        println("Vikram::LifeCycle::splash, onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        println("Vikram::LifeCycle::splash, onStop")
    }
}