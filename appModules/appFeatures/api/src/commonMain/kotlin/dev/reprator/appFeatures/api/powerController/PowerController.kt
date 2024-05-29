package dev.reprator.appFeatures.api.powerController

import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
interface PowerController {
    suspend fun shouldSaveData(): SaveData
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
sealed class SaveData {
    data object Disabled : SaveData()
    data class Enabled(val reason: SaveDataReason) : SaveData()
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
enum class SaveDataReason {
    PREFERENCE,
    SYSTEM_DATA_SAVER,
    SYSTEM_POWER_SAVER,
}
