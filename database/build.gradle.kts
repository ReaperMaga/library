plugins {
    kotlin("jvm")
}

class SourceSetEntry(val name : String, val version : String) {
    val artifact : String
        get() = "database-${name}"
}

val sourceSetEntries = listOf<SourceSetEntry>(
    SourceSetEntry("exposed", "0.1.3"),
)

version = "0.1.0"

sourceSets {
    sourceSetEntries.forEach {
        create(it.name)
    }
}

sourceSetEntries.forEach {
    tasks.register<Jar>("${it.name}Jar") {
        val sourceSet = sourceSets[it.name]
        from(sourceSet.output)
        archiveBaseName.set(it.artifact)
        archiveVersion.set(it.version)
    }
}

configure<PublishingExtension> {
    publications {
        sourceSetEntries.forEach {
            create<MavenPublication>(it.name) {
                val task = tasks.named("${it.name}Jar")
                artifact(tasks.named("${it.name}Jar"))
                artifactId = it.artifact
                version = it.version

                val compileDeps = configurations["${it.name}CompileClasspath"]
                val runtimeDeps = configurations["${it.name}RuntimeClasspath"]

                pom {
                    withXml {
                        asNode().appendNode("dependencies").apply {
                            runtimeDeps.resolvedConfiguration.firstLevelModuleDependencies.forEach {
                                appendNode("dependency").apply {
                                    appendNode("groupId", it.moduleGroup)
                                    appendNode("artifactId", it.moduleName)
                                    appendNode("version", it.moduleVersion)
                                    appendNode("scope", "runtime")
                                }
                            }
                            compileDeps.resolvedConfiguration.firstLevelModuleDependencies.forEach {
                                appendNode("dependency").apply {
                                    appendNode("groupId", it.moduleGroup)
                                    appendNode("artifactId", it.moduleName)
                                    appendNode("version", it.moduleVersion)
                                    appendNode("scope", "compile")
                                }
                            }
                        }

                    }
                }
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

fun DependencyHandlerScope.api(child : String, dependency : Any) {
    add("${child}Api", dependency)
}

fun DependencyHandlerScope.compileOnly(child : String, dependency : Any) {
    add("${child}CompileOnly", dependency)
}