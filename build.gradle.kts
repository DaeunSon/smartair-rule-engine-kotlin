plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "com.daeun.smartair"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.mockk:mockk:1.13.13")
}

tasks.test {
    useJUnitPlatform()
    minHeapSize = "512m"
    maxHeapSize = "2048m"
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.daeun.smartair.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}