import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version libs.versions.kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.server.lib.base)
    implementation(projects.server.lib.baseKtor)

    implementation(libs.exposed.dao)
    implementation(libs.koin.ktor)

    testImplementation(projects.server.lib.testModule)
    testImplementation(projects.server.lib.commonFeatureImpl)
    testImplementation(libs.ktor.client.content.negotiation)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "${JavaVersion.VERSION_17}"
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        }
    }
}