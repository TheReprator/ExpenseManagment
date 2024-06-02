package dev.reprator.baseUi.ui

import android.os.Build
import androidx.compose.runtime.Composable

@Composable
actual fun ReportDrawnWhen(predicate: () -> Boolean) {
    // ReportDrawnWhen routinely causes crashes on API < 25:
    // https://issuetracker.google.com/issues/260506820
    if (Build.VERSION.SDK_INT >= 25) {
        androidx.activity.compose.ReportDrawnWhen(predicate)
    }
}
