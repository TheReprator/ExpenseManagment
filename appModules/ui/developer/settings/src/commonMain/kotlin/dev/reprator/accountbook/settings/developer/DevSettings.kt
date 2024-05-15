package dev.reprator.accountbook.settings.developer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.reprator.screens.DevSettingsScreen
import me.tatarka.inject.annotations.Inject

@Inject
class DevSettingsUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is DevSettingsScreen -> {
            ui<DevSettingsUiState> { state, modifier ->
                DevSettings(state, modifier)
            }
        }

        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DevSettings(
    state: DevSettingsUiState,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Developer Setting title") },
                navigationIcon = {
                    IconButton(onClick = { eventSink(DevSettingsUiEvent.NavigateUp) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxWidth(),
        ) {

            item {
                Preference(
                    title = "Open log",
                    modifier = Modifier.clickable {
                        state.eventSink(DevSettingsUiEvent.NavigateLog)
                    },
                )
            }
        }
    }
}

@Composable
fun Preference(
    title: String,
    modifier: Modifier = Modifier,
    summary: (@Composable () -> Unit)? = null,
    control: (@Composable () -> Unit)? = null,
) {
    Surface(modifier = modifier) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                )

                if (summary != null) {
                    ProvideTextStyle(
                        MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    ) {
                        summary()
                    }
                }
            }

            control?.invoke()
        }
    }
}
