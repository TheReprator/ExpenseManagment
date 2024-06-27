package dev.reprator.accountbook.language

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.accountbook.language.data.dataSource.LanguageRemoteDataSource
import dev.reprator.accountbook.language.data.repositoryImpl.LanguageDataSourceImpl
import dev.reprator.accountbook.language.data.repositoryImpl.remote.LanguageRemoteDataSourceImpl
import dev.reprator.accountbook.language.domain.repository.LanguageRepository
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface LanguageComponent {
    @IntoSet
    @Provides
    @ActivityScope
    fun bindLanguageUiPresenterFactory(factory: LanguageUiPresenterFactory): Presenter.Factory = factory

    @IntoSet
    @Provides
    @ActivityScope
    fun bindLanguageUiFactory(factory: LanguageUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    fun bindLanguageRemoteDataSource(bind: LanguageRemoteDataSourceImpl): LanguageRemoteDataSource = bind

    @Provides
    @ActivityScope
    fun bindLanguageRepository(bind: LanguageDataSourceImpl): LanguageRepository = bind
}
