plugins {
    kotlin("multiplatform") version "2.2.10"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

kotlin {
    js {
        nodejs {
        }
        binaries.executable()
        useCommonJs()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(npm("express", "> 4.0.0 < 5.0.0"))
                implementation("dev.chriskrueger:kotlin-express:1.2.0")
            }
        }
    }
}