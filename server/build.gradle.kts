import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

group = "dev.reprator.accountbook"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

ktor {
    fatJar {
        archiveFileName.set("Reprator-cashBook.jar")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.server.lib.base)
    implementation(projects.server.lib.baseKtor)
    implementation(projects.server.lib.commonFeatureImpl)

    implementation(projects.server.api.language)
    implementation(projects.server.api.splash)
    implementation(projects.server.api.country)
    implementation(projects.server.api.userIdentity)

    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.server.config.yaml)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)
    implementation(libs.logback)

    // testing
    testImplementation(projects.server.lib.testModule)
   // testImplementation(libs.test.ktor.server)
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
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