import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version libs.versions.kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.server.lib.baseKtor)
    implementation(projects.server.lib.base)
    implementation(projects.server.lib.commonFeatureImpl)

    api(libs.ktor.server.netty)
    api(libs.ktor.server.core)

    api(libs.exposed.core)
    implementation(libs.exposed.h2Db)

    api(libs.test.junit5)
    api(libs.test.junit5.suite)
    api(libs.test.junit5.runtime)
    api(libs.test.koin)
    api(libs.test.koin.junit5)
    api(libs.test.kotlin)

    api(libs.ktor.client.mock)
    api(libs.test.coroutine)
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}