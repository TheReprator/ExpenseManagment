package dev.reprator.baseUi.behaviour

import androidx.compose.runtime.Composable

@Composable
expect fun ReportDrawnWhen(predicate: () -> Boolean)
