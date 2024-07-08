package dev.reprator.accountbook.common.ui.resources

val Strings: Map<String, AccountbookStrings> = mapOf(
  "en" to EnAccountbookStrings,
)

object Locales {
  const val EN = "en"
}

data class AccountbookStrings(
  val accountNameUnknown: String
)

expect fun String.fmt(vararg args: Any?): String
