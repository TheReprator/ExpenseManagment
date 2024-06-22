package dev.reprator.accountbook

import dev.reprator.appFeatures.api.utility.ApplicationLifeCycle
import kotlinx.browser.document
import org.w3c.dom.events.Event

private const val APP_EVENT_FOREGROUND = "visibilitychange"
private const val APP_FOREGROUND_STATE_VISIBLE = "visible"

class AppStateListener(private val lifeCycle: ApplicationLifeCycle) {

    private val callback: (event: Event) -> Unit = { event ->
        val eventContainer = event.target?.asDynamic()
        val state = eventContainer["visibilityState"] as? String
        if (null != state) {
            if (APP_FOREGROUND_STATE_VISIBLE == state)
                lifeCycle.isAppInForeGround()
            else
                lifeCycle.isAppInBackground()
        }
    }

    fun removeCallback() {
        document.removeEventListener(APP_EVENT_FOREGROUND, callback)
    }

    fun startListener() {
        document.addEventListener(APP_EVENT_FOREGROUND, callback);
    }
}