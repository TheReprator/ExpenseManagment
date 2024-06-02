rootProject.name = "AccountBook-KMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {

    repositories {
        google {
            content {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenCentral()
        mavenLocal()
    }
}

include(
    ":appModules:base",
    ":appModules:navigation",
    ":appModules:base-ui",
    ":appModules:appFeatures:api",
    ":appModules:appFeatures:impl",
    ":appModules:ui:root",
    ":appModules:ui:splash",
    ":appModules:ui:settings",
    ":appModules:ui:developer:log",
    ":appModules:ui:developer:settings",
    ":appModules:sharedFlavour:common",
    ":appModules:sharedFlavour:prod",
    ":appModules:sharedFlavour:qa",
    ":android-app:app",
    ":desktop-app",
    ":webApp:Js"
)


//include(
//    ":server", ":server:modals", ":server:lib:commonFeatureImpl", ":server:lib:base",
//    ":server:lib:base-ktor", ":server:lib:testModule", ":server:api:language", ":server:api:splash",
//    ":server:api:country", ":server:api:userIdentity"
//)