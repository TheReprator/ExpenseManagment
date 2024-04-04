package dev.reprator.accountbook.theme

import accountbook_kmp.composeapp.generated.resources.Res
import accountbook_kmp.composeapp.generated.resources.dm_sans_bold
import accountbook_kmp.composeapp.generated.resources.dm_sans_medium
import accountbook_kmp.composeapp.generated.resources.dm_sans_regular
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font


@OptIn(ExperimentalResourceApi::class)
val DmSansFontFamily: FontFamily
@Composable get() = FontFamily(
  Font(Res.font.dm_sans_bold,  FontWeight.Bold, FontStyle.Normal),
  Font(Res.font.dm_sans_medium,  FontWeight.Medium, FontStyle.Normal),
  Font(Res.font.dm_sans_regular,  FontWeight.Normal, FontStyle.Normal)
)
