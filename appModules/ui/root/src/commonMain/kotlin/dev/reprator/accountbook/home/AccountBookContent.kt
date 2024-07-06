package dev.reprator.accountbook.home

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.continuityRetainedStateRegistry
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import com.slack.circuit.runtime.screen.Screen
import dev.reprator.appFeatures.api.analytics.AppAnalytics
import kotlinx.collections.immutable.ImmutableList
import me.tatarka.inject.annotations.Inject
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.baseUi.overlay.LocalNavigator
import dev.reprator.baseUi.theme.AccountBookTheme
import dev.reprator.baseUi.ui.LocalWindowSizeClass
import dev.reprator.baseUi.ui.rememberCoroutineScope
import dev.reprator.baseUi.ui.shouldUseDarkColors
import dev.reprator.baseUi.ui.shouldUseDynamicColors
import dev.reprator.screens.AccountBookScreen
import dev.reprator.screens.UrlScreen
import kotlinx.coroutines.CoroutineScope

interface AccountBookContent {
    @Composable
    fun Content(
        backstack: SaveableBackStack,
        navigator: Navigator,
        onOpenUrl: (String) -> Boolean,
        modifier: Modifier,
    )
}


@Inject
class DefaultAccountBookContent(
    private val circuit: Circuit,
    private val analytics: AppAnalytics,
    private val rootViewModel: (CoroutineScope) -> RootViewModel,
    private val preferences: AccountbookPreferences,
    private val imageLoader: ImageLoader,
    private val logger: Logger,
) : AccountBookContent {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalCoilApi::class)
    @Composable
    override fun Content(
        backstack: SaveableBackStack,
        navigator: Navigator,
        onOpenUrl: (String) -> Boolean,
        modifier: Modifier,
    ) {
        val coroutineScope = rememberCoroutineScope()
        remember { rootViewModel(coroutineScope) }

        val uriHandler = LocalUriHandler.current

        val accountBookNavigator: Navigator = remember(navigator) {
            AccountBookNavigator(navigator, backstack, uriHandler, logger)
        }

        // Launch an effect to track changes to the current back stack entry, and push them
        // as a screen views to analytics
        LaunchedEffect(backstack.topRecord) {
            val topScreen = backstack.topRecord?.screen as? AccountBookScreen
            analytics.trackScreenView(
                name = topScreen?.name ?: "unknown screen",
                arguments = topScreen?.arguments,
            )
        }

        setSingletonImageLoaderFactory { imageLoader }

        CompositionLocalProvider(
            LocalNavigator provides accountBookNavigator,
            LocalWindowSizeClass provides calculateWindowSizeClass(),
            LocalRetainedStateRegistry provides continuityRetainedStateRegistry(),
        ) {
            CircuitCompositionLocals(circuit) {
                AccountBookTheme(
                    useDarkColors = preferences.shouldUseDarkColors(),
                    useDynamicColors = preferences.shouldUseDynamicColors(),
                ) {
                    Home(
                        backStack = backstack,
                        navigator = accountBookNavigator,
                        modifier = modifier,
                    )
                }
            }
        }
    }
}

private class AccountBookNavigator(
    private val navigator: Navigator,
    private val backStack: SaveableBackStack,
    private val uriHandler: UriHandler,
    private val logger: Logger,
) : Navigator {

    override fun goTo(screen: Screen) : Boolean {
        logger.e { "goTo. Screen: $screen. Current stack: ${backStack.toList()}" }

        return when (screen) {
            is UrlScreen -> {
                //onOpenUrl(screen.url)
                uriHandler.openUri(screen.url)
                false
            }
            else -> navigator.goTo(screen)
        }
    }

    override fun pop(result: PopResult?): Screen? {
        logger.e { "pop. Current stack: ${backStack.toList()}" }
        return navigator.pop(result)
    }

    override fun resetRoot(
        newRoot: Screen,
        saveState: Boolean,
        restoreState: Boolean,
    ): ImmutableList<Screen> {
        logger.d { "resetRoot: newRoot:$newRoot. Current stack: ${backStack.toList()}" }
        return navigator.resetRoot(newRoot, saveState, restoreState)
    }

    override fun peek(): Screen? = navigator.peek()

    override fun peekBackStack(): ImmutableList<Screen> = navigator.peekBackStack()
}
