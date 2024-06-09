import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget


plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {

    applyDefaultHierarchyTemplate()

    jvm("desktop")
    androidTarget()

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
            implementation(projects.appModules.base)
            
            implementation(libs.ktor.client.core)
//            implementation(projects.appModules.data.models)

            implementation(libs.kotlininject.runtime)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.java)
        }

        appleMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
    }
}

android {
    namespace = "dev.reprator.appFeatures.remoteApi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}