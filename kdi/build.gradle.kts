plugins {
    kotlin("jvm")
}

version = "0.3.0"

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
