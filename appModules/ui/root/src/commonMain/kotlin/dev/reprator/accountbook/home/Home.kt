package dev.reprator.accountbook.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.Navigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import dev.reprator.baseUi.ui.LocalWindowSizeClass

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