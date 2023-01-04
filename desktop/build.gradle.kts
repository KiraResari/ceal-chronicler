import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin(Dependencies.Plugins.kotlinMultiplatform)
    id(Dependencies.Plugins.compose).version(Versions.compose)
}

group = Namespaces.cealChronicler
version = Versions.cealChronicler

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = Versions.jvm
        }
    }
    sourceSets {
        val jvmMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            resources.srcDirs("src/jvmMain/resources")
            dependencies {
                implementation(compose.desktop.currentOs)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation(project(":shared"))
                implementation(project(":shared-ui"))
                implementation(Dependencies.Koin.core)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Ceal_Chronicler"
            macOS {
                bundleID = Namespaces.cealChronicler
            }
        }
    }
}