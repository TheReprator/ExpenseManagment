package dev.reprator.common.prod.inject

import android.app.Activity
import dev.reprator.accountbook.home.AccountBookContent
import dev.reprator.common.inject.SharedActivityComponent
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
