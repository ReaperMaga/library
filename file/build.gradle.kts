plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.1.10"
}

class SourceSetEntry(val name: String, val version: String) {
    val artifact: String
        get() = "file-${name}"
}

val sourceSetEntries = listOf<SourceSetEntry>(
    SourceSetEntry("gson", "0.2.0"),
    SourceSetEntry("hocon", "0.2.0")
)

version = "0.1.0"


sourceSets {
    sourceSetEntries.forEach {
        create(it.name)
    }
}

sourceSetEntries.forEach {
    tasks.register<Jar>("${it.name}Jar") {
        from(sourceSets[it.name].output)
        archiveBaseName.set(it.artifact)
        archiveVersion.set(it.version)
    }
}

configure<PublishingExtension> {
    publications {
        sourceSetEntries.forEach {
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
    implementation("gson", "com.google.code.gson:gson:2.13.1")
    compileOnly("gson", sourceSets["main"].output)
    compileOnly("gson", project(":common"))

    // Hocon dependencies
    implementation("hocon", "org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.8.0")

    // Test dependencies
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// Functions
fun DependencyHandlerScope.implementation(child: String, dependency: Any) {
    add("${child}Implementation", dependency)
}

fun DependencyHandlerScope.compileOnly(child: String, dependency: Any) {
    add("${child}CompileOnly", dependency)
}