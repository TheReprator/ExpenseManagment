package dev.reprator.accountbook.di

import dev.reprator.accountbook.screen.AccountBookContent
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class WindowComponent(
  @Component val applicationComponent: DesktopApplicationComponent,
) : SharedUiComponent {
  abstract val accountBookContent: AccountBookContent
  companion object
}
