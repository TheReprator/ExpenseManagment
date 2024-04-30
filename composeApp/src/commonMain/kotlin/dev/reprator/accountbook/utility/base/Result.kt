package dev.reprator.accountbook.utility.base

import kotlinx.coroutines.CancellationException

inline fun Result<*>.onException(
  block: (Throwable) -> Unit,
) {
  val e = exceptionOrNull()
  when {
    e is CancellationException -> throw e
    e != null -> block(e)
  }
}
