import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version libs.versions.kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

dependencies {
    implementation(projects.server.lib.base)

    api(libs.ktor.server.core)

    api(libs.exposed.core)
    api(libs.exposed.jodatime)

    api(libs.ktor.client.core)
    api(libs.ktor.server.serialization.jackson)
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}