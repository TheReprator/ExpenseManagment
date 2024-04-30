package dev.reprator.accountbook.di

import dev.reprator.accountbook.screen.AccountBookUiViewController
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController

@ActivityScope
@Component
abstract class HomeUiControllerComponent(
  @Component val applicationComponent: IosApplicationComponent,
) : SharedUiComponent {

  abstract val uiViewControllerFactory: () -> UIViewController

  @Provides
  @ActivityScope
  fun uiViewController(bind: AccountBookUiViewController): UIViewController = bind()

  companion object
}
