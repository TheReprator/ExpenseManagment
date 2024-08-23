package dev.reprator.accountbook.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Inject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.runtime.ui.ui
import dev.reprator.baseUi.overlay.LocalNavigator
import dev.reprator.baseUi.overlay.showInBottomSheet
import dev.reprator.baseUi.ui.AppLoader
import dev.reprator.baseUi.ui.EmptyContent
import dev.reprator.baseUi.ui.ErrorContent
import dev.reprator.screens.LanguageScreen
import dev.reprator.screens.SplashScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import dev.reprator.accountbook.common.ui.resources.Res
import dev.reprator.accountbook.common.ui.resources.account_name_unknown
import dev.reprator.accountbook.common.ui.resources.logo

@Inject
class SplashUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is SplashScreen -> {
            ui<SplashUiState> { state, modifier ->
                SplashUi(state, modifier)
            }
        }

        else -> null
    }
}

@Composable
internal fun SplashUi(
    viewState: SplashUiState,
    modifier: Modifier = Modifier,
) {
    SplashUi(
        viewState = viewState,
        onReload = { viewState.eventSink(SplashUiEvent.Reload) },
        onRemoveError = { id ->
            viewState.eventSink(SplashUiEvent.ClearMessage(id))
            viewState.eventSink(SplashUiEvent.Reload)
        },
        modifier = modifier,
    )
}

@Composable
internal fun SplashUi(
    viewState: SplashUiState,
    onReload: () -> Unit,
    onRemoveError: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .testTag("accountbook_testTag_splash"),
    ) {
        if (viewState.isLoading) {
            AppLoader()
            return@Surface
        }

        if ((null != viewState.message) && (viewState.message.message.isNotBlank())) {

            ErrorContent(buttonName = stringResource(Res.string.account_name_unknown), errorDescription = viewState.message.message, onButtonClick = {
                onRemoveError(viewState.message.id)
            })
            return@Surface
        }

        if (viewState.data.languageList.isEmpty() && viewState.data.imageList.isEmpty() && (!viewState.isLoading)) {
            EmptyContent(
                title = { Text(text = "Empty Splash") },
                prompt = { Text(text = "No Splash Fetched") },
                graphic = { Text(text = "\uD83D\uDCFC") },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 64.dp),
            )
            return@Surface
        }

        SplashUi(viewState = viewState, login = {}, dashBoard = {})
    }
}

@Composable
private fun SplashUi(
    viewState: SplashUiState,
    login: () -> Unit,
    dashBoard: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val scope = rememberCoroutineScope()
    val overlayHost = LocalOverlayHost.current
    val navigator = LocalNavigator.current

    ContentWithOverlays {
        SplashUi({
            scope.launch {
                overlayHost.showInBottomSheet(
                    screen = LanguageScreen(9),
                    hostNavigator = navigator,
                )
            }
        })
    }
}

@Composable
private fun SplashUi(showLanguage: () -> Unit = {}, modifier: Modifier = Modifier) {

    val paddingModifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer).fillMaxSize()
    ) {

        SplashHeader(showLanguage, paddingModifier)

        SplashContent(paddingModifier)
    }
}

@Composable
private fun SplashHeader(showLanguage: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {
        Image(
            modifier = Modifier.requiredSize(100.dp, 60.dp)
                .clip(CircleShape),
            painter = painterResource(Res.drawable.logo),
            contentDescription = ""
        )

        Row(modifier = Modifier.clickable {
            showLanguage()
        }.then(modifier)) {
            Text(text = "Language", style = MaterialTheme.typography.titleSmall)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "contentDescription")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SplashContent(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy((-30).dp)) {

        val pagerState = rememberPagerState(pageCount = {
            10
        })

        SplashUiPager(pagerState, modifier)
        SplashBottomView(pagerState, modifier)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.SplashUiPager(pagerState: PagerState, modifier: Modifier = Modifier) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.background(Color.Blue).weight(1f).then(modifier)
    ) { page ->
        Text(
            text = "Page: $page",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SplashBottomView(pagerState: PagerState, modifier: Modifier = Modifier) {
    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            println("Page changed to $page")
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        tonalElevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.Green).then(modifier)
        ) {

            SplashUiPagerIndicator(pagerState)

            Text("Let's test it")

            val coroutineScope = rememberCoroutineScope()
            Button(onClick = {
                coroutineScope.launch {
                    // Call scroll to on pagerState
                    pagerState.animateScrollToPage(5)
                }
            }) {
                Text("Skip")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SplashUiPagerIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
            )
        }
    }
}

@Composable
@Preview
fun SimpleComposablePreview() {
    SplashUi()
}
