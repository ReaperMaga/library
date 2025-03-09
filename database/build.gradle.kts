plugins {
    kotlin("jvm")
}

class SourceSetEntry(val name: String, val version: String) {
    val artifact: String
        get() = "file-${name}"
}

val sourceSetEntries = listOf<SourceSetEntry>(
    SourceSetEntry("exposed", "0.1.0"),
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
    // Exposed dependencies
    val exposedVersion = "0.59.0"
    implementation("exposed", "org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("exposed", "org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("exposed", "org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("exposed", "com.h2database:h2:2.3.232")
    implementation("exposed", "com.zaxxer:HikariCP:6.2.1")


    implementation("exposed", project(":common"))


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
fun DependencyHandlerScope.implementation(child : String, dependency : Any) {
    add("${child}Implementation", dependency)
}

fun DependencyHandlerScope.compileOnly(child : String, dependency : Any) {
    add("${child}CompileOnly", dependency)
}