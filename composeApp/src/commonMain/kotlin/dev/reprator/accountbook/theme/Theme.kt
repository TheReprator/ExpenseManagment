package dev.reprator.accountbook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AccountBookTheme(
  useDarkColors: Boolean = isSystemInDarkTheme(),
  useDynamicColors: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = colorScheme(useDarkColors, useDynamicColors),
    typography = AccountBookTypography,
    shapes = AccountBookShapes,
    content = content,
  )
}
