package dev.reprator.accountbook.developer.log

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface DevLogComponent {
    @IntoSet
    @Provides
    @ActivityScope
    fun bindDevLogPresenterFactory(factory: DevLogUiPresenterFactory): Presenter.Factory = factory

    @IntoSet
    @Provides
    @ActivityScope
    fun bindDevLogUiFactoryFactory(factory: DevLogUiFactory): Ui.Factory = factory
}
