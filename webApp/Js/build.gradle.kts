import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
}

kotlin {

    js(IR) {
        browser {
            runTask(Action {
                mainOutputFileName = "main.bundle.js"
            })
            webpackTask(Action {
                mainOutputFileName = "main.bundle.js"
            })
        }
        generateTypeScriptDefinitions()
        binaries.executable()
    }

    sourceSets {

        jsMain.dependencies {
            implementation(projects.appModules.sharedFlavour.qa)
            implementation(libs.ktor.client.js)
        }
    }
}