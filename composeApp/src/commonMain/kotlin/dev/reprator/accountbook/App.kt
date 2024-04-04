package dev.reprator.accountbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import accountbook_kmp.composeapp.generated.resources.Res
import accountbook_kmp.composeapp.generated.resources.compose_multiplatform
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import dev.reprator.accountbook.shared.Greeting
import dev.reprator.accountbook.theme.AccountBookTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {

    var isDark by remember {
        mutableStateOf(true)
    }

    AccountBookTheme(
        useDarkColors = isDark,
        useDynamicColors = false,
    ) {
        Surface(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)) {
            Home({
                isDark = !isDark
            }, Modifier)
        }
    }


}

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun Home(
    click: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // var showContent by remember { mutableStateOf(false) }

    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { click() }, Modifier.background(MaterialTheme.colorScheme.primary)) {
            Text("Click me!", style = MaterialTheme.typography.titleLarge)
        }
        val greeting = remember { Greeting().greet() }
        Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text("Compose: $greeting", style = MaterialTheme.typography.titleLarge)
        }
    }
}