package dev.reprator.accountbook.settings.developer

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface DevSettingsComponent {

    @IntoSet
    @Provides
    @ActivityScope
    fun bindDevSettingsPresenterFactory(factory: DevSettingsUiPresenterFactory): Presenter.Factory = factory

    @IntoSet
    @Provides
    @ActivityScope
    fun bindDevSettingsUiFactoryFactory(factory: DevSettingsUiFactory): Ui.Factory = factory

}
