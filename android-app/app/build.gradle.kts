import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.internal.lint.AndroidLintAnalysisTask
import com.android.build.gradle.internal.lint.LintModelWriterTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "dev.reprator.accountbook"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.reprator.accountbook"
        versionCode = 1
        versionName = "1.0"

        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("release/app-debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        create("release") {
            if (rootProject.file("release/app-release.jks").exists()) {
                storeFile = rootProject.file("release/app-release.jks")
                storePassword = properties["ACCOUNTBOOK_RELEASE_KEYSTORE_PWD"]?.toString() ?: ""
                keyAlias = "accountbook"
                keyPassword = properties["ACCOUNTBOOK_RELEASE_KEY_PWD"]?.toString() ?: ""
            }
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
        // Disable lintVital. Not needed since lint is run on CI
        checkReleaseBuilds = false
        // Ignore any tests
        ignoreTestSources = true
        // Make the build fail on any lint errors
        abortOnError = true
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs["debug"]
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".debug"
        }

        release {
            signingConfig = signingConfigs.findByName("release") ?: signingConfigs["debug"]
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
    }

    flavorDimensions += "mode"
    productFlavors {
        create("qa") {
            dimension = "mode"
            // This is a build with Chucker enabled
            proguardFiles("proguard-rules-chucker.pro")
            versionNameSuffix = "-qa"
        }

        create("standard") {
            dimension = "mode"
            // Standard build is always ahead of the QA builds as it goes straight to
            // the alpha channel. This is the 'release' flavour
            versionCode = (android.defaultConfig.versionCode ?: 0) + 1
        }
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        managedDevices {
            devices {
                create<ManagedVirtualDevice>("api34") {
                    device = "Pixel 6"
                    apiLevel = 34
                    systemImageSource = "aosp"
                }
            }
        }

        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

androidComponents {
    // Ignore the standardDebug variant
    beforeVariants(
        selector()
            .withBuildType("debug")
            .withFlavor("mode" to "standard"),
    ) { variant ->
        variant.enable = false
    }

    onVariants(selector().withBuildType("release")) { variant ->
        variant.packaging.resources.run {
            // Exclude AndroidX version files. We only do this in the release build so that
            // Layout Inspector continues to work for debug
            excludes.add("META-INF/*.version")
            // Exclude the Firebase/Fabric/other random properties files
            excludes.addAll("/*.properties", "META-INF/*.properties")
        }
    }
}

dependencies {
    qaImplementation(projects.appModules.sharedFlavour.qa)
    qaImplementation(libs.leakCanary)

    standardImplementation(projects.appModules.sharedFlavour.prod)

    implementation(libs.androidx.activity.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)

    implementation(libs.kotlin.coroutines.android)

    implementation(libs.google.firebase.crashlytics)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

composeCompiler {
    // Enable 'strong skipping'
    // https://medium.com/androiddevelopers/jetpack-compose-strong-skipping-mode-explained-cbdb2aa4b900
    enableStrongSkippingMode.set(true)

    // Needed for Layout Inspector to be able to see all of the nodes in the component tree:
    //https://issuetracker.google.com/issues/338842143
    includeSourceInformation.set(true)

    if (project.providers.gradleProperty("accountbook.enableComposeCompilerReports").isPresent) {
        val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
        reportsDestination.set(composeReports)
        metricsDestination.set(composeReports)
    }
}

// Workaround for:
// Task 'generateDebugUnitTestLintModel' uses this output of task
// 'generateResourceAccessorsForAndroidUnitTest' without declaring an explicit or
// implicit dependency.
tasks.matching { it is AndroidLintAnalysisTask || it is LintModelWriterTask }.configureEach {
    mustRunAfter(tasks.matching { it.name.startsWith("generateResourceAccessorsFor") })
}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.gms.googleServices.get().pluginId)
    apply(plugin = libs.plugins.firebase.crashlytics.get().pluginId)
}

fun DependencyHandler.qaImplementation(dependencyNotation: Any) =
    add("qaImplementation", dependencyNotation)

fun DependencyHandler.standardImplementation(dependencyNotation: Any) =
    add("standardImplementation", dependencyNotation)
