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

include(":composeApp")


include(
    ":server", ":server:modals", ":server:lib:commonFeatureImpl", ":server:lib:base",
    ":server:lib:base-ktor", ":server:lib:testModule", ":server:api:language", ":server:api:splash",
    ":server:api:country", ":server:api:userIdentity"
)