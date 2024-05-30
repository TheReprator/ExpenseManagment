import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

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

    js(IR) {
        browser()
    }
    
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

        val commonMain by getting {
            dependencies {
                implementation(projects.appModules.base)
                implementation(projects.appModules.appFeatures.api)
                implementation(projects.appModules.baseUi)
                implementation(projects.appModules.navigation)

                implementation(libs.circuit.retained)
                implementation(libs.circuitx.gestureNavigation)
                implementation(libs.circuit.overlay)

                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }
    }
}

android {
    namespace = "dev.reprator.accountbook.home"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}