package dev.reprator.accountbook

import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class WindowStateListener (private val lifeCycle: ApplicationLifeCycle)  : WindowAdapter() {

    override fun windowActivated(e: WindowEvent?) {
        super.windowActivated(e)
        println("vikram::DEsktop:: windowActivated:: ${e.toString()}")
    }

    override fun windowClosed(e: WindowEvent?) {
        super.windowClosed(e)
        println("vikram::DEsktop:: windowClosed:: ${e.toString()}")
    }

    override fun windowClosing(e: WindowEvent?) {
        super.windowClosing(e)
        println("vikram::DEsktop:: windowClosing:: ${e.toString()}")
    }

    override fun windowDeactivated(e: WindowEvent?) {
        super.windowDeactivated(e)
        println("vikram::DEsktop:: windowDeactivated:: ${e.toString()}")
    }

    override fun windowDeiconified(e: WindowEvent?) {
        super.windowDeiconified(e)
        lifeCycle.isAppInForeGround()
        println("vikram::DEsktop:: windowDeiconified:: ${e.toString()}")
    }

    override fun windowGainedFocus(e: WindowEvent?) {
        super.windowGainedFocus(e)
        println("vikram::DEsktop:: windowGainedFocus:: ${e.toString()}")
    }

    override fun windowIconified(e: WindowEvent?) {
        super.windowIconified(e)
        lifeCycle.isAppInBackground()
        println("vikram::DEsktop:: windowIconified:: ${e.toString()}")
    }

    override fun windowLostFocus(e: WindowEvent?) {
        super.windowLostFocus(e)
        println("vikram::DEsktop:: windowLostFocus:: ${e.toString()}")
    }

    override fun windowOpened(e: WindowEvent?) {
        super.windowOpened(e)
        println("vikram::DEsktop:: windowOpened:: ${e.toString()}")
    }

    override fun windowStateChanged(e: WindowEvent?) {
        super.windowStateChanged(e)
        println("vikram::DEsktop:: windowStateChanged:: ${e.toString()}")
    }
}