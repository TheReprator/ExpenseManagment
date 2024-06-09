package dev.reprator.accountbook.api

import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

expect interface AccountBookApiPlatformComponent

interface AccountBookApiComponent : AccountBookApiPlatformComponent {

    @Provides
    @ApplicationScope
    fun bindApiService(bind: AccountBookApiService): ApiService = bind
}
