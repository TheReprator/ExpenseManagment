package dev.reprator.accountbook.settings

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = 31)
internal actual val DynamicColorsAvailable: Boolean = Build.VERSION.SDK_INT >= 31
internal actual val OpenSourceLicenseAvailable: Boolean = true
