plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "library"
include("common", "quarkus", "cdi", "file", "database")
