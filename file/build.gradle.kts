plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.1.10"
}

class Child(val name: String, val version: String) {
    val artifact: String
        get() = "file-${name}"
}

val children = listOf<Child>(
    Child("gson", "0.1.0"),
    Child("hocon", "0.1.0")
)

version = "0.1.0"


sourceSets {
    children.forEach {
        create(it.name)
    }
}

children.forEach {
    tasks.register<Jar>("${it.name}Jar") {
        from(sourceSets[it.name].output)
        archiveBaseName.set(it.artifact)
        archiveVersion.set(it.version)
    }
}

configure<PublishingExtension> {
    publications {
        children.forEach {
            create<MavenPublication>(it.name) {
                artifact(tasks.named("${it.name}Jar"))
                artifactId = it.artifact
                version = it.version
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Gson dependencies
    add("gsonImplementation", "com.google.code.gson:gson:2.12.1")
    add("gsonCompileOnly", sourceSets["main"].output)
    add("gsonCompileOnly", project(":common"))

    // Hocon dependencies
    add("hoconImplementation", "org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.8.0")

    // Test dependencies
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}