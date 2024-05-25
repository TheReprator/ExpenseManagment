package dev.reprator.accountbook.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class TestOb: DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        println("Vikram::LifeCycle::home, onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        println("Vikram::LifeCycle::home, onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        println("Vikram::LifeCycle::home, onResume")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        println("Vikram::LifeCycle::home, onDestroy")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        println("Vikram::LifeCycle::home, onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        println("Vikram::LifeCycle::home, onStop")
    }
}