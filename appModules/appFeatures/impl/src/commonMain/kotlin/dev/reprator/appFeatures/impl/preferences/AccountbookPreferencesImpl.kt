package dev.reprator.appFeatures.impl.preferences

import dev.reprator.core.util.AppCoroutineDispatchers
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences
import dev.reprator.appFeatures.api.preferences.AccountbookPreferences.Theme
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject
import kotlin.jvm.JvmInline

@Inject
class AccountbookPreferencesImpl(
    private val settings: Lazy<KStore<AccountBookSetting>>,
    private val dispatchers: AppCoroutineDispatchers,
) : AccountbookPreferences {

    override suspend fun setTheme(theme: Theme) = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(theme = theme)
        }
    }

    override suspend fun observeTheme(): Flow<Theme> = settings.value.updates.map {
        it?.theme ?: Theme.SYSTEM
    }

    override suspend fun toggleUseDynamicColors() = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(
                dynamicColorEnable = PrefDynamicColors(!it.dynamicColorEnable.value)
            )
        }
    }

    override suspend fun observeUseDynamicColors() =
        settings.value.updates.map {
            it?.dynamicColorEnable?.value ?: false
        }

    override suspend fun toggleUseLessData() = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(
                shouldUseLessData = PrefLessData(
                    !it.shouldUseLessData.value
                )
            )
        }
    }

    override suspend fun observeUseLessData() = settings.value.updates.map {
        it?.shouldUseLessData?.value ?: false
    }


    override suspend fun getUseLessData(): Boolean = withContext(dispatchers.io) {
        settings.value.get()?.shouldUseLessData?.value ?: true
    }

    override suspend fun toggleReportAppCrashes() = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(
                crashEnabled = PrefReportCrash(
                    !it.crashEnabled.value
                )
            )
        }
    }

    override suspend fun observeReportAppCrashes() = settings.value.updates.map {
        it?.crashEnabled?.value ?: true
    }

    override suspend fun toggleReportAnalytics() = withContext(dispatchers.io) {
        settings.value.update {
            it?.copy(
                analyticsEnabled = PrefReportAnalytics(
                    !it.analyticsEnabled.value
                )
            )
        }
    }

    override suspend fun observeReportAnalytics() = settings.value.updates.map {
        it?.analyticsEnabled?.value ?: true
    }

}

@Serializable
data class AccountBookSetting(
    val theme: Theme,
    val dynamicColorEnable: PrefDynamicColors,
    val crashEnabled: PrefReportCrash,
    val analyticsEnabled: PrefReportAnalytics,
    val shouldUseLessData: PrefLessData
) {
    constructor() : this(
        Theme.SYSTEM, PrefDynamicColors(), PrefReportCrash(), PrefReportAnalytics(), PrefLessData()
    )
}

    @Serializable
    @JvmInline
    value class PrefLessData (val value: Boolean) {
        constructor(): this(false)
    }

    @Serializable
    @JvmInline
    value class PrefDynamicColors (val value: Boolean) {
        constructor(): this(false)
    }

    @Serializable
    @JvmInline
    value class PrefReportCrash(val value: Boolean) {
        constructor(): this(true)
    }

    @Serializable
    @JvmInline
    value class PrefReportAnalytics(val value: Boolean) {
        constructor(): this(true)
    }
