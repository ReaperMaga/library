plugins {
    kotlin("jvm") version "2.0.21"
    id("maven-publish")
}

group = "dev.reapermaga"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

subprojects {
    group = "dev.reapermaga.library"
}





