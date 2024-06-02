package dev.reprator.appFeatures.impl.powerController

import dev.reprator.appFeatures.api.powerController.PowerController
import dev.reprator.appFeatures.api.powerController.SaveData
import dev.reprator.appFeatures.api.powerController.SaveDataReason
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import me.tatarka.inject.annotations.Inject
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@Inject
@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
class DefaultPowerController(
    private val preferences: Lazy<AccountbookPreferences>,
) : PowerController {
    override suspend fun shouldSaveData(): SaveData = when {
        preferences.value.getUseLessData() -> SaveData.Enabled(SaveDataReason.PREFERENCE)
        else -> SaveData.Disabled
    }
}
