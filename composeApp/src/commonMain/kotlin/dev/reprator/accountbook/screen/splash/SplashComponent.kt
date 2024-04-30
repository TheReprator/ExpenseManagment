package dev.reprator.accountbook.screen.splash

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.accountbook.di.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SplashComponent {
    @IntoSet
    @Provides
    @ActivityScope
    fun bindSplashPresenterFactory(factory: SplashUiPresenterFactory): Presenter.Factory = factory

    @IntoSet
    @Provides
    @ActivityScope
    fun bindSplashUiFactory(factory: SplashUiFactory): Ui.Factory = factory
}
