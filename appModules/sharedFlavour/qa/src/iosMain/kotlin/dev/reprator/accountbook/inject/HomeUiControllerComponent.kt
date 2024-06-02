package dev.reprator.accountbook.inject

import dev.reprator.accountbook.home.AccountBookUiViewController
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController

@ActivityScope
@Component
abstract class HomeUiControllerComponent(
    @Component val applicationComponent: IosApplicationComponent,
) : QaUiComponent {

    abstract val uiViewControllerFactory: () -> UIViewController

    @Provides
    @ActivityScope
    fun uiViewController(bind: AccountBookUiViewController): UIViewController = bind()

    companion object
}
