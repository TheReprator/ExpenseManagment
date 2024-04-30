package dev.reprator.accountbook.di

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.reprator.accountbook.cleanArchitecture.data.dataSource.SplashRemoteDataSource
import dev.reprator.accountbook.cleanArchitecture.data.repositoryImpl.SplashDataSourceImpl
import dev.reprator.accountbook.cleanArchitecture.dataSource.remote.SplashRemoteDataSourceImpl
import dev.reprator.accountbook.cleanArchitecture.domain.repository.SplashRepository
import dev.reprator.accountbook.screen.splash.SplashUiFactory
import dev.reprator.accountbook.screen.splash.SplashUiPresenterFactory
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SplashComponent {
    @Provides
    fun provideSplashRemoteDataSource(
        bind: SplashRemoteDataSourceImpl,
    ): SplashRemoteDataSource = bind

    @Provides
    fun provideSplashRepository(
        bind: SplashDataSourceImpl,
    ): SplashRepository = bind
}


interface SplashUiComponent {
    
    @IntoSet
    @Provides
    @ActivityScope
    fun bindSplashPresenterFactory(factory: SplashUiPresenterFactory): Presenter.Factory = factory

    @IntoSet
    @Provides
    @ActivityScope
    fun bindSplashUiFactory(factory: SplashUiFactory): Ui.Factory = factory
}