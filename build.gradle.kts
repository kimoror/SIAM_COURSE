plugins {
    java
}

group = "kimoror"
version = "1.0-SNAPSHOT"

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "kimoror.siam.SIAM"
    }
}

java {
    withSourcesJar()
}

tasks.findByName("build")?.mustRunAfter("clean")

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.clojars.org")
        name = "Clojars"
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.6")
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-security:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.5.6")
    implementation("org.mapstruct:mapstruct:1.4.2.Final")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")

    runtimeOnly("org.postgresql:postgresql:42.3.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}