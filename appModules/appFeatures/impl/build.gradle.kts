import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

android {
    namespace = "dev.reprator.appFeatures.impl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

kotlin {

    applyDefaultHierarchyTemplate()

    androidTarget()
    jvm("desktop")


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

        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.appModules.base)
            implementation(projects.appModules.appFeatures.api)

            api(libs.multiplatformsettings.core)
            api(libs.multiplatformsettings.coroutines)

            implementation(libs.kermit)
            implementation(libs.kotlininject.runtime)
        }

        androidMain.dependencies {
            implementation(libs.google.firebase.analytics)
            implementation(libs.kotlininject.runtime)

            implementation(libs.crashkios.crashlytics)
            implementation(libs.google.firebase.crashlytics)
            implementation(libs.timber)

            implementation(libs.google.firebase.perf)

            implementation(libs.androidx.core)
        }

        appleMain.dependencies {
            implementation(libs.crashkios.crashlytics)
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
