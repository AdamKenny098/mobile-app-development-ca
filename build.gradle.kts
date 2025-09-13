plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.setu.placemark"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation ("io.github.microutils:kotlin-logging:2.1.23")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}