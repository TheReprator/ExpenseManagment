import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {

    androidTarget()
    jvm("desktop")

    js(IR) {
        browser()
    }

    //iosX64()
    iosArm64()
    iosSimulatorArm64()

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("mobileDesktop") {
                withAndroidTarget()
                withJvm()
                withApple()
            }
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(projects.appModules.base)
            implementation(projects.appModules.appFeatures.api)
            api(projects.appModules.navigation)

            api(compose.material3)
            api(compose.animation)

            implementation(compose.components.resources)
            api(projects.appModules.resources.strings)
            api(libs.lyricist.library)

            api(libs.compose.material3.windowsizeclass)

            api(libs.circuit.foundation)
            api(libs.circuit.overlay)

            api(libs.coil.core)
            api(libs.coil.network)
            api(libs.coil.compose)

            implementation(libs.uuid)
        }

        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "dev.reprator.baseUi"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
}