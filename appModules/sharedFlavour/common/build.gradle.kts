import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
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

    //iosX64()
    iosArm64()
    iosSimulatorArm64()

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.configureEach {
            // Add linker flag for SQLite. See:
            // https://github.com/touchlab/SQLiter/issues/77
            linkerOpts("-lsqlite3")

            // Workaround for https://youtrack.jetbrains.com/issue/KT-64508
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
                    // We replace the default library names with something more unique (the project path).
                    // This allows us to avoid the annoying issue of `duplicate library name: foo_commonMain`
                    // https://youtrack.jetbrains.com/issue/KT-57914
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

        val commonMain by getting {
            dependencies {
                api(projects.appModules.base)
                api(projects.appModules.appFeatures.api)
                api(projects.appModules.baseUi)
                api(projects.appModules.appFeatures.impl)
                api(projects.appModules.ui.root)
                api(projects.appModules.ui.splash)
            }
        }
    }
}

android {
    namespace = "dev.reprator.accountbook.common"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

