package dev.reprator.accountbook.splash

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.accountbook.splashDomain.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.splashDomain.data.repositoryImpl.SplashDataSourceImpl
import dev.reprator.accountbook.splashDomain.dataSource.remote.SplashRemoteDataSourceImpl
import dev.reprator.accountbook.splashDomain.domain.repository.SplashRepository
import dev.reprator.core.inject.ActivityScope
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

    @Provides
    @ActivityScope
    fun bindSplashRemoteDataSource(bind: SplashRemoteDataSourceImpl): SplashRemoteDataSource = bind

    @Provides
    @ActivityScope
    fun bindSplashRepository(bind: SplashDataSourceImpl): SplashRepository = bind
}
