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

        val commonMain by getting {
            dependencies {
                implementation(projects.appModules.base)
                implementation(projects.appModules.appFeatures.api)
                api(projects.appModules.navigation)

                api(compose.material3)
                api(compose.animation)
                api(compose.components.resources)
                api(compose.components.uiToolingPreview)

                api(libs.compose.material3.windowsizeclass)

                api(libs.circuit.foundation)
                api(libs.circuit.overlay)

                api(libs.coil.core)
                api(libs.coil.network)
                api(libs.coil.compose)

                implementation(libs.uuid)
            }
        }

        val mobileDesktopMain by creating {
            dependencies {
                dependsOn(commonMain)
            }
        }

        val desktopMain by getting {
            dependencies {
                dependsOn(mobileDesktopMain)
            }
        }

        val appleMain by getting {
            dependencies {
                dependsOn(mobileDesktopMain)
            }
        }
        
        val androidMain by getting {
            dependencies {
                dependsOn(mobileDesktopMain)
                api(libs.androidx.activity.compose)
            }
        }
    }
}

android {
    namespace = "dev.reprator.baseUi"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
}