plugins {
    kotlin("jvm")
}

version = "0.3.0"

val guavaDependency = "com.google.guava:guava:33.4.8-jre"

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    compileOnly(guavaDependency)
    testImplementation(guavaDependency)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
