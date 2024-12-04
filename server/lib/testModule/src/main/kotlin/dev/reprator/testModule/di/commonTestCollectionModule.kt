package dev.reprator.testModule.di

import org.koin.core.KoinApplication
import org.koin.ksp.generated.*

fun KoinApplication.commonFeatureTestCollectionModule() {
    modules(AppDBModule().module, AppTestCoreModule().module,
        JWTOverrideModule().module, defaultModule)
}
