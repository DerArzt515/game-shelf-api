val ktor_version = "2.0.3"
val kotlin_version = "1.7.10"
val logback_version = "1.2.9"
val exposed_version = "0.36.2"
val h2_version = "1.4.200"
val ktorm_version ="3.5.0"


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
    implementation("dev.cavalier:game-shelf-model-jvm:development")
    implementation("org.ktorm:ktorm-core:3.5.0")
    implementation("me.liuwj.ktorm:ktorm-support-postgresql:3.1.0")
    implementation("org.postgresql:postgresql:42.4.0")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.mockk:mockk:1.12.5")
    testImplementation("org.assertj:assertj-core:3.23.1")
}