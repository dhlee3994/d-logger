plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
    `maven-publish`
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation(project(":d-logger-core"))

    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework:spring-web")
    compileOnly("org.springframework:spring-webmvc")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

publishing {
    publications {
        create<MavenPublication>("spring") {
            from(components["java"])
            artifactId = "d-logger-spring"

            pom {
                name.set("D-Logger-Spring")
                description.set("Spring library for D-Logger.")
                url.set("https://github.com/dhlee3994/d-logger")

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("dhlee3994")
                        name.set("DongHyeon Lee")
                        email.set("dhlee3994@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/dhlee3994/d-logger.git")
                    developerConnection.set("scm:git:ssh://github.com/dhlee3994/d-logger.git")
                    url.set("https://github.com/dhlee3994/d-logger.git")
                }
            }
        }
    }
}