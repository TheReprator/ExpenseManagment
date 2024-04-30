package dev.reprator.accountbook.screen

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
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
import dev.reprator.accountbook.screen.navigation.UrlScreen
import dev.reprator.accountbook.theme.AccountBookTheme
import dev.reprator.accountbook.utility.LocalWindowSizeClass
import dev.reprator.accountbook.utility.overlay.LocalNavigator
import kotlinx.collections.immutable.ImmutableList
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import dev.reprator.accountbook.utility.rememberCoroutineScope

typealias AccountBookContent = @Composable (
  backstack: SaveableBackStack,
  navigator: Navigator,
  onOpenUrl: (String) -> Unit,
  modifier: Modifier,
) -> Unit

@Inject
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalCoilApi::class)
@Composable
fun AccountBookContent(
    @Assisted backstack: SaveableBackStack,
    @Assisted navigator: Navigator,
    @Assisted onOpenUrl: (String) -> Unit,
    circuit: Circuit,
    imageLoader: ImageLoader,
    logger: Logger,
    @Assisted modifier: Modifier = Modifier,
) {
  val coroutineScope = rememberCoroutineScope()

  val accountBookNavigator: Navigator = remember(navigator) {
    AccountBookNavigator(navigator, backstack, onOpenUrl, logger)
  }

  setSingletonImageLoaderFactory { imageLoader }

    CompositionLocalProvider(
      LocalNavigator provides accountBookNavigator,
      LocalWindowSizeClass provides calculateWindowSizeClass(),
      LocalRetainedStateRegistry provides continuityRetainedStateRegistry(),
    ) {
      CircuitCompositionLocals(circuit) {
        var isDark by remember {
            mutableStateOf(true)
          }
        
          AccountBookTheme(
            useDarkColors = isDark,
            useDynamicColors = false
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

private class AccountBookNavigator(
  private val navigator: Navigator,
  private val backStack: SaveableBackStack,
  private val onOpenUrl: (String) -> Unit,
  private val logger: Logger,
) : Navigator {
  override fun goTo(screen: Screen) {
    logger.d { "goTo. Screen: $screen. Current stack: ${backStack.toList()}" }

    when (screen) {
      is UrlScreen -> onOpenUrl(screen.url)
      else -> navigator.goTo(screen)
    }
  }

  override fun pop(result: PopResult?): Screen? {
    logger.d { "pop. Current stack: ${backStack.toList()}" }
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
