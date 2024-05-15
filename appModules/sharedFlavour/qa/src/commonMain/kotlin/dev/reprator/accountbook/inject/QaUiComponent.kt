package dev.reprator.accountbook.inject

import dev.reprator.accountbook.developer.log.DevLogComponent
import dev.reprator.accountbook.settings.developer.DevSettingsComponent

interface QaUiComponent :
    SharedUiComponent, DevSettingsComponent, DevLogComponent
