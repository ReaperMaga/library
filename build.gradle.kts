plugins {
    kotlin("jvm") version "2.0.21"
}


group = "dev.reapermaga"
version = "0.1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    group = "dev.reapermaga.library"

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(21)
    }
}



