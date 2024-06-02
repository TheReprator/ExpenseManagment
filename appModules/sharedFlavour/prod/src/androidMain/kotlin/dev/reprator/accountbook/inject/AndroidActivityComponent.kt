package dev.reprator.accountbook.inject

import android.app.Activity
import dev.reprator.accountbook.home.AccountBookContent
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScope
@Component
abstract class AndroidActivityComponent(
    @get:Provides override val activity: Activity,
    @Component val applicationComponent: AndroidApplicationComponent,
) : SharedActivityComponent, ProdUiComponent {

    abstract val accountBookContent: AccountBookContent

    companion object
}
