package dev.reprator.accountbook.home

import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.Provides

interface RootUiComponent {
    @Provides
    @ActivityScope
    fun bindAccountBookContent(impl: DefaultAccountBookContent): AccountBookContent = impl
}
