package dev.reprator.appFeatures.impl.powerController

import android.annotation.SuppressLint
import android.app.Application
import android.net.ConnectivityManager
import android.os.PowerManager
import dev.reprator.appFeatures.api.powerController.SaveData
import dev.reprator.appFeatures.api.powerController.SaveDataReason
import androidx.core.content.getSystemService
import dev.reprator.appFeatures.api.powerController.PowerController
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidPowerController(
    private val context: Application,
    private val preferences: Lazy<AccountbookPreferences>,
) : PowerController {
    private val powerManager: PowerManager by lazy { context.getSystemService()!! }
    private val connectivityManager: ConnectivityManager by lazy { context.getSystemService()!! }

    override suspend fun shouldSaveData(): SaveData = when {
        preferences.value.getUseLessData() -> {
            SaveData.Enabled(SaveDataReason.PREFERENCE)
        }

        powerManager.isPowerSaveMode -> {
            SaveData.Enabled(SaveDataReason.SYSTEM_POWER_SAVER)
        }

        isBackgroundDataRestricted() -> {
            SaveData.Enabled(SaveDataReason.SYSTEM_DATA_SAVER)
        }

        else -> SaveData.Disabled
    }

    @SuppressLint("NewApi")
    private fun isBackgroundDataRestricted(): Boolean {
        return connectivityManager.restrictBackgroundStatus ==
                ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED
    }
}
