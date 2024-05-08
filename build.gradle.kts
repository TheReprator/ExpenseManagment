plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false

    alias(libs.plugins.gms.googleServices) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}

buildscript {
    dependencies {
        // Yuck. Need to force kotlinpoet:1.16.0 as that is what buildconfig uses.
        // CMP 1.6.0-x uses kotlinpoet:1.14.x. Gradle seems to force 1.14.x which then breaks
        // buildconfig.
        classpath("com.squareup:kotlinpoet:1.16.0")
    }
}