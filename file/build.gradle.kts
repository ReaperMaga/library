plugins {
    kotlin("jvm")
}

val gsonArtifact = "file-gson"
val gsonVersion = "0.1.0"
version = "0.1.0"

sourceSets {
    create("gson")
}

tasks.register<Jar>("gsonJar") {
    from(sourceSets["gson"].output)
    archiveBaseName.set(gsonArtifact)
    archiveVersion.set(gsonVersion)
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("gson") {
            artifact(tasks.named("gsonJar"))
            artifactId = gsonArtifact
            version = gsonVersion
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

    // Test dependencies
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}