import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
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

        val desktopMain by getting

        commonMain.dependencies {
            api(libs.circuit.runtime)
        }

        targets.configureEach {
            val isAndroidTarget = platformType == KotlinPlatformType.androidJvm
            compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions {
                        if (isAndroidTarget) {
                            freeCompilerArgs.addAll(
                                "-P",
                                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=dev.reprator.screens.Parcelize",
                            )
                        }
                    }
                }
            }
        }
    }
}

android {
    namespace = "dev.reprator.screens"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}