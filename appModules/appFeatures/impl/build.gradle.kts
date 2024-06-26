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

    applyDefaultHierarchyTemplate()

    androidTarget()
    jvm("desktop")

    js(IR) {
        browser()
    }

    //iosX64()
    iosArm64()
    iosSimulatorArm64()

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

        val commonMain by getting {
            dependencies {
                implementation(projects.appModules.base)
                implementation(projects.appModules.appFeatures.api)

                api(libs.kstore)

                implementation(libs.kermit)
                implementation(libs.kotlininject.runtime)
            }
        }

        val mobileDesktopMain by creating {
            dependencies {
                dependsOn(commonMain)
                implementation(libs.kstore.file)
            }
        }

        val desktopMain by getting {
            dependencies {
                dependsOn(mobileDesktopMain)
                implementation(libs.ktor.client.java)
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(mobileDesktopMain)
                implementation(libs.google.firebase.analytics)
                implementation(libs.kotlininject.runtime)

                implementation(libs.crashkios.crashlytics)
                implementation(libs.google.firebase.crashlytics)
                implementation(libs.timber)

                implementation(libs.google.firebase.perf)

                implementation(libs.androidx.core)
                implementation(libs.ktor.client.android)
            }
        }

        val appleMain by getting {
            dependencies {
                dependsOn(mobileDesktopMain)
                implementation(libs.crashkios.crashlytics)
                implementation(libs.ktor.client.darwin)
            }
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
