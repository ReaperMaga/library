plugins {
    kotlin("jvm") version "2.0.21"
}

version = "0.1.2"

dependencies {
    implementation("com.google.guava:guava:33.2.1-jre")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}