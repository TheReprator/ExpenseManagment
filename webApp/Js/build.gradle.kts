plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
}

kotlin {

    js(IR) {
        useCommonJs()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
            }
        }
        binaries.executable()
    }

    sourceSets {

        jsMain.dependencies {
            implementation(projects.appModules.sharedFlavour.qa)
            implementation(libs.ktor.client.js)
        }
    }
}