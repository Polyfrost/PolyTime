@file:Suppress("PropertyName")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.polyfrost.org/releases") // Adds the Polyfrost maven repository to get Polyfrost Gradle Toolkit
    }
    plugins {
        val pgtVersion = "0.6.7" // Sets the default versions for Polyfrost Gradle Toolkit
        id("org.polyfrost.multi-version.root") version pgtVersion
    }
}

val mod_name: String by settings

// Configures the root project Gradle name based on the value in `gradle.properties`
rootProject.name = mod_name
rootProject.buildFileName = "root.gradle.kts"

// Adds all of our build target versions to the classpath if we need to add version-specific code.
listOf(
    "1.8.9-forge", // Update this if you want to remove/add a version, along with `build.gradle.kts` and `root.gradle.kts`.
    "1.8.9-fabric",
    "1.12.2-forge",
    "1.12.2-fabric",
    "1.16.5-forge",
    "1.16.5-fabric",
    "1.17.1-forge",
    "1.17.1-fabric",
    "1.18.1-forge",
    "1.18.1-fabric",
    "1.19.4-forge",
    "1.19.4-fabric",
    "1.20.4-forge",
    "1.20.4-fabric",
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle.kts"
    }
}