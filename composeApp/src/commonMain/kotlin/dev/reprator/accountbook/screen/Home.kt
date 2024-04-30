package dev.reprator.accountbook.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material.icons.outlined.Weekend
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import dev.reprator.accountbook.utility.LocalWindowSizeClass

@Composable
internal fun Home(
  backStack: SaveableBackStack,
  navigator: Navigator,
  modifier: Modifier = Modifier,
) {
  val windowSizeClass = LocalWindowSizeClass.current

  val rootScreen by remember(backStack) {
    derivedStateOf { backStack.last().screen }
  }

  Scaffold {
    Row(modifier = Modifier.fillMaxSize()) {
      ContentWithOverlays(
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight(),
      ) {
        NavigableCircuitContent(
          navigator = navigator,
          backStack = backStack,
          decoration = remember(navigator) {
            GestureNavigationDecoration(onBackInvoked = navigator::pop)
          },
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}
