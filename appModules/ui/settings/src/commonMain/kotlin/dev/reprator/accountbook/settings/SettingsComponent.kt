package dev.reprator.accountbook.settings

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SettingsComponent {
    @IntoSet
    @Provides
    @ActivityScope
    fun bindSettingsPresenterFactory(factory: SettingsUiPresenterFactory): Presenter.Factory = factory

    @IntoSet
    @Provides
    @ActivityScope
    fun bindSettingsUiFactoryFactory(factory: SettingsUiFactory): Ui.Factory = factory
}
