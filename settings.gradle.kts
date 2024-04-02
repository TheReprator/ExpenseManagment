rootProject.name = "AccountBook-KMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp",":shared")


include(":server",":server:modals",":server:lib:commonFeatureImpl",":server:lib:base",
        ":server:lib:base-ktor",":server:lib:testModule",":server:api:language",":server:api:splash",
        ":server:api:country",":server:api:userIdentity")