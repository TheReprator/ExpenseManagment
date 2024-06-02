package dev.reprator.baseUi.ui

import androidx.compose.runtime.Composable

@Composable
expect fun ReportDrawnWhen(predicate: () -> Boolean)
