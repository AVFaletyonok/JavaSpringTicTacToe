plugins {
    id("java-library")
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.5.16"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

group = "avfaletyonok.tictactoe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Управление зависимостями Spring Boot (BOM)
dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.session:spring-session-jdbc")

    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Тесты
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // JUnit 5 BOM (Bill of Materials) для управления версиями юнит-тестов
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

// Настройка запуска тестов
tasks.test {
    useJUnitPlatform()
}


tasks.named<Jar>("jar") {
    manifest {
        attributes( "Main-Class" to "avfaletyonok.tictactoe.Main" )
    }
}