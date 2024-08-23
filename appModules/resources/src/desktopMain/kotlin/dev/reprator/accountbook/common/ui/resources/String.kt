package dev.reprator.accountbook.common.ui.resources

actual fun String.fmt(vararg args: Any?): String = format(*args)
