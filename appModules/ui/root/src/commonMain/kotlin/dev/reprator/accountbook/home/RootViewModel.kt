package dev.reprator.accountbook.home

import dev.reprator.appFeatures.api.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class RootViewModel(
    @Assisted private val coroutineScope: CoroutineScope,
    private val logger: Logger,
) {
    
    fun clear() {
        coroutineScope.cancel()
    }
}
