plugins {
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

    java {

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.lyricist.core)
            }
        }

        val desktopMain by getting
    }
}

android {
    namespace = "dev.reprator.accountbook.common.ui.resources.strings"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
