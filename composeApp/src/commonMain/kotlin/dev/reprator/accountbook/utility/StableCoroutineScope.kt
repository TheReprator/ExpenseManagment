package dev.reprator.accountbook.utility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope

/**
 * Returns a [StableCoroutineScope] around a [androidx.compose.runtime.rememberCoroutineScope].
 * This is useful for event callback lambdas that capture a local scope variable to launch new
 * coroutines, as it allows them to be stable.
 */
@Composable
fun rememberCoroutineScope(): StableCoroutineScope {
  val scope = androidx.compose.runtime.rememberCoroutineScope()
  return remember { StableCoroutineScope(scope) }
}

/** @see rememberCoroutineScope */
@Stable
class StableCoroutineScope(scope: CoroutineScope) : CoroutineScope by scope
