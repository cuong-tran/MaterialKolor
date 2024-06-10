import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.publish)
    id("maven-publish")
}

kotlin {
    explicitApi()

    applyDefaultHierarchyTemplate()

    androidTarget {
        publishAllLibraryVariants()
    }

    jvm()

    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    macosX64()
    macosArm64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { target ->
        target.binaries.framework {
            baseName = "material-kolor"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(libs.colormath)

                api(project(":material-color-utilities"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    compileSdk = libs.versions.sdk.compile.get().toInt()
    namespace = "com.materialkolor"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(jdkVersion = 11)
    }
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") { // Replace 'maven' with a suitable name
                groupId = "com.github.cuong-tran" // Replace with your actual group ID
                artifactId = "material-kolor" // Replace with your actual artifact ID
                version = "1.6.2" // Replace with your actual version

                // Add the artifacts you want to publish (e.g., JAR, AAR)
                from(components["kotlin"]) // Assuming you have a 'release' component
            }
        }
    }
}
