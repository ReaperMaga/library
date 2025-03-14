plugins {
    kotlin("jvm") version "2.0.21"
}

version = "0.1.10"

dependencies {
    implementation("at.favre.lib:bcrypt:0.10.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}