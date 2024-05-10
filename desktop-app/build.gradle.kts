import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
}

kotlin {

    jvm()

    sourceSets {

        jvmMain.dependencies {
            implementation(projects.appModules.sharedFlavour.qa)

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.coroutines.swing)
            implementation(libs.ktor.client.java)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.reprator.accountbook"
            packageVersion = "1.0.0"
        }
    }
}