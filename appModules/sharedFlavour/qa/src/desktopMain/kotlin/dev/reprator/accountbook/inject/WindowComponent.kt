package dev.reprator.accountbook.inject

import dev.reprator.accountbook.home.AccountBookContent
import dev.reprator.core.inject.ActivityScope
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class WindowComponent(
    @Component val applicationComponent: DesktopApplicationComponent,
) : QaUiComponent {
    abstract val accountBookContent: AccountBookContent

    companion object
}
