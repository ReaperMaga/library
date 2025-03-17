plugins {
    kotlin("jvm") version "2.0.21"
    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "4.0.0"
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

        withJavadocJar()
        withSourcesJar()
    }

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "Repsy"
                url = uri("https://repo.repsy.io/mvn/reapermaga/library")
                credentials {
                    username = env.fetch("REPSY_USERNAME", "")
                    password = env.fetch("REPSY_PASSWORD", "")
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





