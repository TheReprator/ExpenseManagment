package dev.reprator.accountbook.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.accountbook.screen.AccountBookContent
import dev.reprator.accountbook.screen.DefaultAccountBookContent
import me.tatarka.inject.annotations.Provides

interface SharedUiComponent : SplashUiComponent {

    @Provides
    @ActivityScope
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()

    @Provides
    @ActivityScope
    fun bindAccountBookContent(impl: DefaultAccountBookContent): AccountBookContent = impl
}
