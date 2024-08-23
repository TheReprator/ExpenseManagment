plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
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
                api(compose.components.resources)
                implementation(libs.kotlin.coroutines.core)
                implementation(compose.runtime)
                implementation(compose.material3)
            }
        }

        
        iosMain {
            dependencies {
              @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
              implementation(compose.components.resources)
            }
          }
        
          commonTest {
            dependencies {
              implementation(kotlin("test"))
              implementation(libs.test.assertk)
            }
          }
        
        val desktopMain by getting {
            dependencies {
              implementation(compose.desktop.currentOs)
            }
          }
    }
}

android {
    namespace = "dev.reprator.accountbook.common.ui.resources"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res")
      }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "dev.reprator.accountbook.common.ui.resources"
  }
