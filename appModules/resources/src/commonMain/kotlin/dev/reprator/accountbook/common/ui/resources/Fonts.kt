package dev.reprator.accountbook.common.ui.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font

val DmSansFontFamily: FontFamily
  @Composable get() = FontFamily(
    Font(Res.font.dm_sans_regular, weight = FontWeight.Normal),
    Font(Res.font.dm_sans_medium, weight = FontWeight.Medium),
    Font(Res.font.dm_sans_bold, weight = FontWeight.Bold),
  )
