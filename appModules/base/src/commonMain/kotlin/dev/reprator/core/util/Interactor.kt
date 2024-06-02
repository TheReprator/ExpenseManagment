package dev.reprator.accountbook.core.util

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeout

abstract class Interactor<in P, R> {
    private val count = atomic(0)
    private val loadingState = MutableStateFlow(count.value)

    val inProgress: Flow<Boolean> = loadingState
        .map { it > 0 }
        .distinctUntilChanged()

    private fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    private fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }

    suspend operator fun invoke(
        params: P,
        timeout: Duration = DefaultTimeout,
    ): Result<R> = try {
        addLoader()
        runCatching {
            withTimeout(timeout) {
                doWork(params)
            }
        }
    } finally {
        removeLoader()
    }

    protected abstract suspend fun doWork(params: P): R

    companion object {
        internal val DefaultTimeout = 5.minutes
    }
}

suspend operator fun <R> Interactor<Unit, R>.invoke(
    timeout: Duration = Interactor.DefaultTimeout,
) = invoke(Unit, timeout)
