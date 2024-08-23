package dev.reprator.baseUi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorContent(
    buttonName: String,
    errorDescription: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {

        Column(modifier = Modifier.align(Alignment.Center)) {

            Button(onClick = onButtonClick) {
                Text(buttonName, style = MaterialTheme.typography.headlineMedium)
            }

            Text(text = errorDescription, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
