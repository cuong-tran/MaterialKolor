pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://www.jitpack.io")
    }
}

plugins {
    id("com.gradle.develocity") version "3.17.4"
}

develocity {
    buildScan {
        publishing.onlyIf { context ->
            context.buildResult.failures.isNotEmpty() && !System.getenv("CI").isNullOrEmpty()
        }
    }
}

rootProject.name = "MaterialKolor"

include(
    ":material-kolor",
    ":material-color-utilities",
)

//include(":demo:composeApp")
