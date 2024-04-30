package dev.reprator.accountbook.di

import android.app.Activity
import dev.reprator.accountbook.screen.AccountBookContent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScope
@Component
abstract class AndroidActivityComponent(
  @get:Provides override val activity: Activity,
  @Component val applicationComponent: AndroidApplicationComponent,
) : SharedActivityComponent, SharedUiComponent {
  abstract val accountBookContent: AccountBookContent

  companion object
}
