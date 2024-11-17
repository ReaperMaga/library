plugins {
    kotlin("jvm") version "2.0.21"
}

group = "dev.reapermaga"
version = "0.1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}
subprojects {
    group = "dev.reapermaga.library"


}



