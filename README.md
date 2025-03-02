# Library

This library is a collection of functions that I have written to help me with my work. 
I have decided to share them with the world in the hope that they will be useful to others.

## Usage with Maven

To use this library in your Maven project, add the following dependency to your `pom.xml` file:

```xml

<repositories>
    <repository>
        <id>repsy</id>
        <name>ReaperMaga Library</name>
        <url>https://repo.repsy.io/mvn/reapermaga/library</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.reapermaga.library</groupId>
        <artifactId>{lib}</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
```

Replace `{lib}` with the name of the library you want to use and `{version}` with the version number.
You can find the available libraries and versions on the [Repsy Repository](https://repo.repsy.io/mvn/reapermaga/library/com/github/reapermaga/library/).

## Usage with Gradle

To use this library in your Gradle project, add the following dependency to your `build.gradle.kts` file:

```kotlin
repositories {
    maven {
        id = "repsy"
        url = uri("https://repo.repsy.io/mvn/reapermaga/library")
    }
}

dependencies {
    implementation("com.github.reapermaga.library:{lib}:{version}")
}
```

Replace `{lib}` with the name of the library you want to use and `{version}` with the version number.
You can find the available libraries and versions on the [Repsy Repository](https://repo.repsy.io/mvn/reapermaga/library/com/github/reapermaga/library/).