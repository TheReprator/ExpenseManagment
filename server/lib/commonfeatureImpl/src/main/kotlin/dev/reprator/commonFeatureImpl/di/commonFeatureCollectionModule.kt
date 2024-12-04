package dev.reprator.commonFeatureImpl.di

import org.koin.core.KoinApplication
import org.koin.ksp.generated.*

fun KoinApplication.commonFeatureCollectionModule() {
    modules(AppCommonModule().module, AppNetworkModule().module, defaultModule)
}
