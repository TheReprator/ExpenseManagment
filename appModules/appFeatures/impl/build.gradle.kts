import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.reprator.appFeatures.impl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
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
    applyDefaultHierarchyTemplate{
        common {
            group("mobileDesktop") {
                withAndroidTarget()
                withJvm()
                withApple()
            }
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.configureEach {
            linkerOpts("-lsqlite3")

            freeCompilerArgs += "-Xdisable-phases=RemoveRedundantCallsToStaticInitializersPhase"
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    // Various opt-ins
                    freeCompilerArgs.addAll(
                        "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                        "-opt-in=kotlinx.cinterop.BetaInteropApi",
                    )
                }
            }
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }

    metadata {
        compilations.configureEach {
            if (name == KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME) {
                compileTaskProvider.configure {
                    val projectPath = path.substring(1).replace(":", "_")
                    this as KotlinCompileCommon
                    moduleName.set("${projectPath}_commonMain")
                }
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

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.serialization.json)
                implementation(libs.ktor.client.logging)

                api(libs.kstore)

                implementation(libs.kermit)
                implementation(libs.kotlininject.runtime)
        }

        val mobileDesktopMain by getting {
            dependencies {
                implementation(libs.kstore.file)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.java)
            }
        }

        androidMain.dependencies {
                implementation(libs.google.firebase.analytics)
                implementation(libs.kotlininject.runtime)

                implementation(libs.crashkios.crashlytics)
                implementation(libs.google.firebase.crashlytics)
                implementation(libs.timber)

                implementation(libs.google.firebase.perf)

                implementation(libs.androidx.core)
                implementation(libs.ktor.client.android)
        }

        appleMain.dependencies {
                implementation(libs.crashkios.crashlytics)
                implementation(libs.ktor.client.darwin)
        }

        jsMain.dependencies {
            implementation(kotlin("stdlib-js"))
            implementation(npm("firebase", "10.6.0"))
            implementation(libs.kstore.storage)
            implementation(libs.ktor.client.js)
        }
    }
}

android {

    namespace = "dev.reprator.appFeatures.impl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}
