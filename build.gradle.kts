import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val activeProfile = System.getenv("PROFILE") ?: "dev"
val imageTag = System.getenv("IMAGE_TAG") ?: "latest"
val repoURL: String? = System.getenv("IMAGE_REPO_URL")

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.cloud.tools.jib") version "3.4.1"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
}

group = "com.vacgom"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-gson:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.processResources {
    dependsOn("initConfig")
}

tasks.register<Copy>("initConfig") {
    from("./CONFIG")
    include("*.yml")
    into("./src/main/resources")
}

jib {
    from {
        image = "amazoncorretto:17-alpine3.18"
    }
    to {
        image = repoURL
        tags = setOf(imageTag)
    }
    container {
        jvmFlags = listOf(
                "-Dspring.profiles.active=${activeProfile}",
                "-Dserver.port=8080",
                "-XX:+UseContainerSupport",
        )
        ports = listOf("8080")
    }
}
