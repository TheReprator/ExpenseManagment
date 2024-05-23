package dev.reprator.appFeatures.impl.powerController

import dev.reprator.appFeatures.api.powerController.PowerController
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface PowerControllerComponent {
    @Provides
    @ApplicationScope
    fun providePowerController(bind: DefaultPowerController): PowerController = bind
}
