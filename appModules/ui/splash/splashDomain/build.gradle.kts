plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    applyDefaultHierarchyTemplate()

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
            }
        }
    }
}
