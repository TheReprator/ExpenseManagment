package dev.reprator.common.inject

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.accountbook.home.RootUiComponent
import dev.reprator.accountbook.splash.SplashComponent
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.Provides

interface SharedUiComponent :
    RootUiComponent,
    SplashComponent {

    @Provides
    @ActivityScope
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()
}
