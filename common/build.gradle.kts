plugins {
    kotlin("jvm") version "2.0.21"
}

version = "0.1.14"

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}