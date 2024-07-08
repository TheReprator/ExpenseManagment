package dev.reprator.baseUi.behaviour

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.lyricist.LanguageTag
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import dev.reprator.accountbook.common.ui.resources.AccountbookStrings
import dev.reprator.accountbook.common.ui.resources.EnAccountbookStrings
import dev.reprator.accountbook.common.ui.resources.Strings

val LocalStrings: ProvidableCompositionLocal<AccountbookStrings> = compositionLocalOf { EnAccountbookStrings }

@Composable
fun rememberStrings(
  languageTag: LanguageTag = "en",
): Lyricist<AccountbookStrings> = rememberStrings(Strings, languageTag)

@Composable
fun ProvideStrings(
  lyricist: Lyricist<AccountbookStrings> = rememberStrings(),
  content: @Composable () -> Unit,
) {
  ProvideStrings(lyricist, LocalStrings, content)
}
