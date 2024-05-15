package dev.reprator.appFeatures.api.powerController

interface PowerController {
    suspend fun shouldSaveData(): SaveData
}

sealed class SaveData {
    data object Disabled : SaveData()
    data class Enabled(val reason: SaveDataReason) : SaveData()
}

enum class SaveDataReason {
    PREFERENCE,
    SYSTEM_DATA_SAVER,
    SYSTEM_POWER_SAVER,
}
