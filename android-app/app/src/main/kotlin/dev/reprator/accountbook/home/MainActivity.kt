package dev.reprator.accountbook.home

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.reprator.accountbook.AccountbookActivity
import dev.reprator.accountbook.AccountbookApplication
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.common.qa.inject.AndroidActivityComponent
import dev.reprator.common.qa.inject.AndroidApplicationComponent
import dev.reprator.common.qa.inject.create
import dev.reprator.screens.SplashScreen
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AccountbookActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdgeForTheme(AccountbookPreferences.Theme.SYSTEM)

        super.onCreate(savedInstanceState)

        val applicationComponent = AndroidApplicationComponent.from(this)
        val component = AndroidActivityComponent.create(this, applicationComponent)

        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val prefs = withContext(applicationComponent.dispatchers.io) {
                    applicationComponent.preferences.observeTheme()
                }
                prefs.collect(::enableEdgeToEdgeForTheme)
            }
        }

        setContent {
            val backstack = rememberSaveableBackStack(listOf(SplashScreen))
            val navigator = rememberCircuitNavigator(backstack)

            component.accountBookContent.Content(
                backstack = backstack,
                navigator = navigator,
                onOpenUrl = { url ->
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(this@MainActivity, Uri.parse(url))
                },
                modifier = Modifier.semantics {
                    // Enables testTag -> UiAutomator resource id
                    @OptIn(ExperimentalComposeUiApi::class)
                    testTagsAsResourceId = true
                },
            )
        }
    }
}

private fun ComponentActivity.enableEdgeToEdgeForTheme(theme: AccountbookPreferences.Theme) {
    val style = when (theme) {
        AccountbookPreferences.Theme.LIGHT -> SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        AccountbookPreferences.Theme.DARK -> SystemBarStyle.dark(Color.TRANSPARENT)
        AccountbookPreferences.Theme.SYSTEM -> SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
    }
    enableEdgeToEdge(statusBarStyle = style, navigationBarStyle = style)
}

private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent {
    return (context.applicationContext as AccountbookApplication).component
}
