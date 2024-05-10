import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {

    applyDefaultHierarchyTemplate()

    androidTarget()
    jvm("desktop")

    //iosX64()
    iosArm64()
    iosSimulatorArm64()

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    sourceSets {

        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.appModules.base)
            implementation(projects.appModules.appFeatures.api)
            api(projects.appModules.navigation)

            api(compose.material3)
            api(compose.animation)
            implementation(compose.components.resources)
            api(libs.compose.material3.windowsizeclass)

            api(libs.circuit.foundation)
            api(libs.circuit.overlay)

            api(libs.coil.core)
            api(libs.coil.network)
            api(libs.coil.compose)

            implementation(libs.uuid)
        }

        androidMain.dependencies {
            api(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "dev.reprator.baseUi"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
}