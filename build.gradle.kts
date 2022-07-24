val ktor_version = 2.0.3
val kotlin_version = 1.7.10
val logback_version = 1.2.9
val kotlin.code.style = official


plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "cavalier.dev"
version = "0.0.1"
application {
    mainClass.set("cavalier.dev.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/DerArzt515/game-shelf-model")
        name = "gameshelf"
        credentials(PasswordCredentials::class)
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("dev.cavalier:game-shelf-model:development")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}