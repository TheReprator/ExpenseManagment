package dev.reprator.appFeatures.api.powerController

import kotlin.native.HiddenFromObjC

@HiddenFromObjC
interface PowerController {
    suspend fun shouldSaveData(): SaveData
}

@HiddenFromObjC
sealed class SaveData {
    data object Disabled : SaveData()
    data class Enabled(val reason: SaveDataReason) : SaveData()
}

@HiddenFromObjC
enum class SaveDataReason {
    PREFERENCE,
    SYSTEM_DATA_SAVER,
    SYSTEM_POWER_SAVER,
}
