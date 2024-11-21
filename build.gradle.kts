plugins {
    kotlin("jvm") version "2.0.21"
    id("maven-publish")
}

group = "com.github.reapermaga"
version = "0.1.0"

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    group = "com.github.reapermaga.library"
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/reapermaga/library")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("PKG_USER")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("PKG_TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}





