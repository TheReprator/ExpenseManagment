package dev.reprator.accountbook

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import dev.reprator.accountbook.di.AndroidActivityComponent
import dev.reprator.accountbook.di.AndroidApplicationComponent
import dev.reprator.accountbook.di.create
import dev.reprator.accountbook.screen.navigation.SplashScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val applicationComponent = AndroidApplicationComponent.from(this)
        val component = AndroidActivityComponent.create(this, applicationComponent)

        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        val prefs = withContext(applicationComponent.dispatchers.io) {
//                            applicationComponent.preferences.observeTheme()
//                        }
//                        prefs.collect(::enableEdgeToEdgeForTheme)
            }
        }

        setContent {
            val backstack = rememberSaveableBackStack(listOf(SplashScreen))
            val navigator = rememberCircuitNavigator(backstack)

            component.tiviContent.accountBookContent(
                backstack,
                navigator,
                { url ->
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(this@MainActivity, Uri.parse(url))
                },
                Modifier.semantics {
                    // Enables testTag -> UiAutomator resource id
                    @OptIn(ExperimentalComposeUiApi::class)
                    testTagsAsResourceId = true
                },
            )
        }
    }
}

private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent {
    return (context.applicationContext as AccountBookApplication).component
}