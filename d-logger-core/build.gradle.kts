plugins {
    kotlin("jvm") version "1.9.25"
    `maven-publish`
}

dependencies {
    api("org.slf4j:slf4j-api:1.7.36")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")

    testImplementation(kotlin("test"))
    testImplementation("ch.qos.logback:logback-classic:1.5.16")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("core") {
            from(components["java"])
            artifactId = "d-logger-core"

            pom {
                name.set("D-Logger-Core")
                description.set("Core library for D-Logger.")
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
