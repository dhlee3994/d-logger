plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "d-logger"
include("d-logger-core")
include("d-logger-spring")
